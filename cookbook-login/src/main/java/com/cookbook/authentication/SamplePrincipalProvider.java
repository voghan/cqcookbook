package com.cookbook.authentication;

import org.apache.jackrabbit.api.security.principal.PrincipalIterator;
import org.apache.jackrabbit.core.security.principal.PrincipalProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Credentials;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.security.Principal;
import java.util.Properties;

/**
 * @author david
 */
public class SamplePrincipalProvider implements PrincipalProvider {
    static final Logger log = LoggerFactory.getLogger(SamplePrincipalProvider.class);

    @Override
    public Principal getPrincipal(String string) {
        log.info("********** - getPrincipal :" + string);
        if ("davidg".contains(string)) {
            return new SamplePrincipal("davidg");
        }

        return null;
    }

    /**
     * @param credentials
     * @return
     */
    private boolean canProvidePrincipal(Credentials credentials) {
        log.info("********** - canProvidePrincipal");
        if (!(credentials instanceof SimpleCredentials)) {
            return false;
        }

        SimpleCredentials sc = (SimpleCredentials) credentials;

        return true; //StringUtils.equals("davidg", sc.getUserID());
    }

    @Override
    public PrincipalIterator findPrincipals(String string) {
        log.info("********** - findPrincipals String");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PrincipalIterator findPrincipals(String string, int i) {
        log.info("********** - findPrincipals");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PrincipalIterator getPrincipals(int i) {
        log.info("********** - getPrincipals int");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PrincipalIterator getGroupMembership(Principal prncpl) {
        log.info("********** - getGroupMembership");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(Properties prprts) {
        log.info("********** - init");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() {
        log.info("********** - close");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canReadPrincipal(Session sn, Principal prncpl) {
        log.info("********** - canReadPrincipal");
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
