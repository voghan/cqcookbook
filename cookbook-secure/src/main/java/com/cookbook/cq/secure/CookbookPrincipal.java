package com.cookbook.cq.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

/**
 * User: bvaughn
 * Date: 11/3/14
 */
public class CookbookPrincipal implements Principal {
    static final Logger log = LoggerFactory.getLogger(CookbookPrincipal.class);
    private String name;

    public CookbookPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
