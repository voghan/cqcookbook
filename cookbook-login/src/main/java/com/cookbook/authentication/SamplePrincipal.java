package com.cookbook.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

/**
 * User: bvaughn
 * Date: 11/3/14
 */
public class SamplePrincipal implements Principal {
    static final Logger log = LoggerFactory.getLogger(SampleLoginModule.class);
    private String name;

    public SamplePrincipal(String name) {
        log.info("********** - SamplePrincipal");
        this.name = name;
    }

    @Override
    public String getName() {
        log.info("********** - getName");
        return name;
    }

    public String getUserId() {
        log.info("********** - getUserId");
        return name;
    }


}
