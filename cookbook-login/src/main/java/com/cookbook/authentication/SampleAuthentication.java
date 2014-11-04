package com.cookbook.authentication;

import org.apache.jackrabbit.core.security.authentication.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;


/**
 * @author david
 */
public class SampleAuthentication implements Authentication {
    private static final Logger log = LoggerFactory.getLogger(SampleAuthentication.class);

    @Override
    public boolean canHandle(Credentials credentials) {
        log.info("********** - canHandle");
        // Don't handle Credentials if they are null
        if (credentials == null) {
            return false;
        }

        // Only handle SimpleCredentials
        if (!(credentials instanceof SimpleCredentials)) {
            return false;
        }

        SimpleCredentials sc = (SimpleCredentials) credentials;

        return true;
    }

    @Override
    public boolean authenticate(Credentials credentials) throws RepositoryException {
        log.info("********** - authenticate");
        if (!(credentials instanceof SimpleCredentials)) {
            return false;
        }

        SimpleCredentials simpleCredentials = (SimpleCredentials) credentials;
        final String userId = simpleCredentials.getUserID();

        return true; //StringUtils.equals(userId, "davidg");
    }
}
