package com.cookbook.cq.secure;

import org.apache.jackrabbit.api.security.principal.PrincipalIterator;
import org.apache.jackrabbit.core.security.principal.PrincipalProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.security.Principal;
import java.util.Properties;

/**
 *
 */
public class CookbookPrincipalProvider implements PrincipalProvider {
    static final Logger log = LoggerFactory.getLogger(CookbookPrincipalProvider.class);

    @Override
    public Principal getPrincipal(String string) {
        CookbookPrincipal principal = null;
        if (CookbookAuthentication.users.containsKey(string)) {
            principal =  new CookbookPrincipal(string);
        }

        return principal;
    }


    @Override
    public PrincipalIterator findPrincipals(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PrincipalIterator findPrincipals(String s, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PrincipalIterator getPrincipals(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PrincipalIterator getGroupMembership(Principal principal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(Properties properties) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canReadPrincipal(Session session, Principal principal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
