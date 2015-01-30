package com.cookbook.cq.secure;

import org.apache.jackrabbit.core.security.authentication.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */
public class CookbookAuthentication implements Authentication {
    private static final Logger log = LoggerFactory.getLogger(CookbookAuthentication.class);

    static final Map users;

    static {
        users =  new HashMap();
        users.put("amsuser1", "mango".toCharArray());
        users.put("amsuser2", "moonstone".toCharArray());
        users.put("amsuser3", "mammoth".toCharArray());
        users.put("amsuser4", "mouse".toCharArray());

    }

    @Override
    public boolean canHandle(Credentials credentials) {
        boolean canHandle = false;

        if (credentials != null && credentials instanceof SimpleCredentials) {
            SimpleCredentials sc = (SimpleCredentials) credentials;
            canHandle = true;
        }

        return canHandle;
    }

    @Override
    public boolean authenticate(Credentials credentials) throws RepositoryException {
        log.info("********** - authenticate");
        boolean authenticated = false;

        if ((credentials instanceof SimpleCredentials)) {
            SimpleCredentials simpleCredentials = (SimpleCredentials) credentials;
            final String userId = simpleCredentials.getUserID();
            final char[] password = simpleCredentials.getPassword();

            if (users.containsKey(userId)) {
                final char[] expected = (char[])users.get(userId);
                authenticated = checkPasswords(password, expected);

            }
        }

        log.info("authentication status:" + authenticated);
        return authenticated;
    }

    private boolean checkPasswords(char[] provided, char[] expected) {
        boolean valid = true;
        if (provided.length == expected.length) {
            for (int i = 0; i < expected.length; i++) {
                if (provided[i] != expected[i]) {
                    valid = false;
                    break;
                }
            }

        } else {
            valid = false;
        }

        return valid;
    }
}
