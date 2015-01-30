package com.cookbook.cq.secure;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.authentication.AbstractLoginModule;
import org.apache.jackrabbit.core.security.authentication.Authentication;
import org.apache.jackrabbit.core.security.authentication.token.TokenBasedAuthentication;
import org.apache.jackrabbit.core.security.principal.EveryonePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 *
 */
public class CookbookLoginModule extends AbstractLoginModule {
    static final Logger log = LoggerFactory.getLogger(CookbookLoginModule.class);

    private UserManager userManager;
    private SessionImpl session;

    private static final EveryonePrincipal EVERYONE_PRINCIPAL = EveryonePrincipal.getInstance();

    @Override
    protected void doInit(CallbackHandler callbackHandler, Session session, Map options)
        throws LoginException {
        try {
            this.session = (SessionImpl) session;
            userManager = this.session.getUserManager();
        } catch (RepositoryException e) {
            throw new LoginException("Unable to initialize SampleLoginModule: " + e.getMessage());
        }
    }

    /**
     * Handles the impersonation of the Principal using the provided
     * Credentials.
     * <p/>
     * Impersonation only occurs if the provided Credentials allow for the
     * impersonation of the Principal.
     *
     * @param principalToImpersonate  Principal to impersonate
     * @param impersonatorCredentials Credentials used to create the
     *                                impersonation subject.
     * @return
     * @throws javax.jcr.RepositoryException
     * @throws javax.security.auth.login.LoginException
     */
    @Override
    protected boolean impersonate(Principal principalToImpersonate,
        Credentials impersonatorCredentials) throws RepositoryException, LoginException {
        Authorizable authorizableToImpersonate =
            userManager.getAuthorizable(principalToImpersonate);
        if (authorizableToImpersonate == null || authorizableToImpersonate.isGroup()) {
            return false;
        }

        Subject impersonatorSubject = getImpersonatorSubject(impersonatorCredentials);
        User userToImpersonate = (User) authorizableToImpersonate;

        if (userToImpersonate.getImpersonation().allows(impersonatorSubject)) {
            return true;
        } else {
            throw new FailedLoginException(
                "attempt to impersonate denied for " + principalToImpersonate.getName());
        }
    }

    /**
     * Principal is the CRX Principal that the Credentials should be
     * authenticated against.
     * <p/>
     * Principal is retrieved from getPrincipal(Credentials credentials) using
     * the Credentials UserId field.
     * <p/>
     * Get the Authentication object
     *
     * @param principal
     * @param credentials
     * @return
     * @throws javax.jcr.RepositoryException
     */
    @Override
    protected Authentication getAuthentication(Principal principal, Credentials credentials)
        throws RepositoryException {
        Authentication authentication = new CookbookAuthentication();
        if (principal == null || !authentication.canHandle(credentials)) {
            authentication = null;
        }

        return authentication;
    }

    /**
     * Get the CRX Principal the credentials should be authenticated against.
     * This is NOT the authentication step, and usually involves looking up the
     * Principal in CRX based on the credentials UserId.
     *
     * @param credentials
     * @return
     */
    @Override
    protected Principal getPrincipal(Credentials credentials) {
        Principal p = null;
        if (credentials instanceof SimpleCredentials) {
            SimpleCredentials sc = (SimpleCredentials) credentials;
            final String userId = sc.getUserID();
            log.info(".... userId:" + userId);
            final CookbookPrincipalProvider
                cookbookPrincipalProvider = new CookbookPrincipalProvider();

            p = cookbookPrincipalProvider.getPrincipal(userId);

            log.info("Principal retrieved from PrincipleProvider: " + p);

            if (p instanceof Group) {
                p = null;
            }
        }




        return p;
    }

    /**
     * commit() is invoked by login() if LoginContext's overall authentication
     * succeeded.
     * <p/>
     * If authentication has succeeded then this method associates relevant
     * Principals and Credentials (instance fields) with this objects Subject
     * (instance field).
     * <p/>
     * The login is considers as succeeded if the credentials field is set. If
     * there is no principal set the login is considered as ignored.
     * <p/>
     * The implementation stores the principal associated to the UserID and all
     * the Groups it is member of with the Subject and in addition adds an
     * instance of Credentials to the Subject's public credentials.
     *
     * @return
     * @throws javax.security.auth.login.LoginException
     */
    @Override
    public boolean commit() throws LoginException {

        Collection<Principal> principals = new ArrayList<Principal>();

        if (!isInitialized() || principal == null) {
            return false;
        }

        if (credentials != null && isAnonymous(credentials)) {
            return super.commit();
        }

        checkValidateUser();

        log.info("Principal name for new user: " + principal.getName());

        // add everyone group, unless principal is everyone
        if (!principal.equals(EVERYONE_PRINCIPAL)) {
            principals.add(EVERYONE_PRINCIPAL);
        }


        if (TokenBasedAuthentication.doCreateToken(credentials)) {
            Session s = null;
            try {
                /*
                 * use a different session instance to create the token node
                 * in order to prevent concurrent modifications with the
                 * shared system session.
                 */
                s = session.createSession(session.getWorkspace().getName());
                Authorizable user = userManager.getAuthorizable(principal.getName());
                Credentials tc = TokenBasedAuthentication.createToken((User) user, credentials,
                    TokenBasedAuthentication.TOKEN_EXPIRATION, s);
                if (tc != null) {
                    subject.getPublicCredentials().add(tc);
                }
            } catch (RepositoryException e) {
                LoginException le = new LoginException("Failed to commit: " + e.getMessage());
                le.initCause(e);
                throw le;
            } finally {
                if (s != null) {
                    s.logout();
                }
            }
        }

        subject.getPrincipals().addAll(principals);
        subject.getPublicCredentials().add(credentials);
        return true;
    }

    private void checkValidateUser() {
        try {
            Authorizable user = userManager.getAuthorizable(principal.getName());


            if (user == null) {
                user = userManager.createUser(credentials.getUserID(), String.copyValueOf(
                    credentials.getPassword()));

                if (user == null) {
                    log.info("Could not create user for " + credentials.getUserID());
                    abort();
                }

                log.info("Created user: " + user.getPrincipal().getName());

                /**
                 * Optionally add the new user to the appropriate groups. This
                 * may require going back to the PrincipalProvider.
                 **/

                final Authorizable authGroup = userManager.getAuthorizable("AMS");
                if (authGroup.isGroup()) {
                    Group group = (Group) authGroup;
                    group.addMember(user);
                }
            }
        } catch (RepositoryException e) {
            log.warn(e.getMessage(), e);
        } catch (LoginException e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * Returns true if this method succeeded or false if this LoginModule should
     * be ignored *
     *
     * @return
     * @throws javax.security.auth.login.LoginException
     */
    @Override
    public boolean logout() throws LoginException {
        if (super.logout()) {
            // LoginModule should not be ignored, proceed with any futher checks
            // and clear any residual LoginModule state

            // Return true if logout was successful
            return true;
        }

        // Return false if this LoginModule should be ignored
        return false;
    }

    /**
     * Returns true if this method succeeded or false if this LoginModule should
     * be ignored
     *
     * @return
     * @throws javax.security.auth.login.LoginException
     */
    @Override
    public boolean abort() throws LoginException {

        if (super.abort()) {
            // LoginModule should not be ignored, proceed with any futher checks
            // and clear any residual LoginModule state
            this.principal = null;
            this.credentials = null;

            // Return true if the abortion was successful
            return true;
        }

        // Return false if this LoginModule should be ignored
        return false;
    }
}

