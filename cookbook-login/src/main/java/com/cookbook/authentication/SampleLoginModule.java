package com.cookbook.authentication;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.authentication.AbstractLoginModule;
import org.apache.jackrabbit.core.security.authentication.Authentication;
import org.apache.jackrabbit.core.security.authentication.token.TokenBasedAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;


public class SampleLoginModule extends AbstractLoginModule {
    static final Logger log = LoggerFactory.getLogger(SampleLoginModule.class);

    private UserManager userManager;
    private Session session;

    @Override
    protected void doInit(CallbackHandler callbackHandler, Session session, Map options) throws LoginException {
        log.info("********** - doInit");
        if (!(session instanceof SessionImpl)) {
            throw new LoginException("Unable to initialize SampleLoginModule: SessionImpl expected.");
        }

        this.session = session;

        try {
            userManager = ((SessionImpl) session).getUserManager();
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
     * @throws RepositoryException
     * @throws javax.security.auth.login.LoginException
     */
    @Override
    protected boolean impersonate(Principal principalToImpersonate, Credentials impersonatorCredentials) throws RepositoryException, LoginException {
        log.info("********** - impersonate");
        Authorizable authorizableToImpersonate = userManager.getAuthorizable(principalToImpersonate);
        if (authorizableToImpersonate == null || authorizableToImpersonate.isGroup()) {
            return false;
        }

        Subject impersonatorSubject = getImpersonatorSubject(impersonatorCredentials);
        User userToImpersonate = (User) authorizableToImpersonate;

        if (userToImpersonate.getImpersonation().allows(impersonatorSubject)) {
            return true;
        } else {
            throw new FailedLoginException("attempt to impersonate denied for " + principalToImpersonate.getName());
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
     * @param creds
     * @return
     * @throws RepositoryException
     */
    @Override
    protected Authentication getAuthentication(Principal principal, Credentials creds) throws RepositoryException {
        log.info("********** - getAuthentication");
        if (principal != null) {
            Authentication authentication = new SampleAuthentication();
            if (authentication.canHandle(creds)) {
                return authentication;
            }
        }
        // No valid user or authentication could not handle the given credentials
        return null;
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
        log.info("********** - getPrincipal");

        final String userId = getUserID(credentials);
        final SamplePrincipalProvider samplePrincipalProvider = new SamplePrincipalProvider();

        final Principal p = samplePrincipalProvider.getPrincipal(userId);

        log.info("Principal retrieved from PrincipleProvider: " + p);

        if (p != null && !(p instanceof Group)) {
            return p;
        }

        return null;
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
        log.info("********** - commit");
        log.info("********** - principal:" + principal);
        log.info("********** - principal:" + credentials);

        try {
            if (!super.commit() || principal == null) {
                return false;
            } else if (credentials == null) {
                abort();
            }

            Authorizable user = userManager.getAuthorizable(principal.getName());

            if (user == null) {
                log.info("User is null; create user for " + credentials.getUserID());

                user = userManager.createUser(credentials.getUserID(), UUID.randomUUID().toString());

                if (user == null) {
                    log.info("Could not create user for " + credentials.getUserID());
                    abort();
                }

                log.info("Created user: " + user.getPrincipal().getName());

                /**
                 * Optionally add the new user to the appropriate groups. This
                 * may require going back to the PrincipalProvider.
                 **/

                principal = user.getPrincipal();
                log.info("Principal name for new user: " + principal.getName());

            } else if (user.isGroup()) {
                log.info("User is a group: " + user.getPrincipal().getName());
                user = null;
            }

            // Handle token creation if requested by the given credentials
            if (user != null) {
                if (user instanceof User && TokenBasedAuthentication.doCreateToken(credentials)) {
                    log.info("Issue a CRX Login Token for this User.");

                    Session noconflictSession = ((SessionImpl) session).createSession(session.getWorkspace().getName());
                    try {
                        Credentials tokenCreds = TokenBasedAuthentication.createToken((User) user, credentials, 100000000, noconflictSession);
                        subject.getPublicCredentials().add(tokenCreds);
                    } finally {
                        noconflictSession.logout();
                    }
                }
                return true;
            }
        } catch (RepositoryException ex) {
            log.warn(ex.getMessage(), ex);
        }

        return false;
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
        log.info("********** - logout");
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
        log.info("********** - abort");

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

