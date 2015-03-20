package com.cookbook.cq.mock;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.Map;

/**
 * User: bvaughn
 * Date: 12/16/14
 */
public class ResourceResolverFactoryMock implements ResourceResolverFactory {
    @Override public ResourceResolver getResourceResolver(Map<String, Object> authenticationInfo)
        throws LoginException {
        return null;
    }

    @Override public ResourceResolver getAdministrativeResourceResolver(
        Map<String, Object> authenticationInfo) throws LoginException {
        return null;
    }

    @Override
    public ResourceResolver getServiceResourceResolver(Map<String, Object> stringObjectMap) throws LoginException {
        return null;
    }
}
