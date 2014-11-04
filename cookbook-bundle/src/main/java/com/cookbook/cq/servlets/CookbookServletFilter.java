package com.cookbook.cq.servlets;

import org.apache.felix.scr.annotations.*;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.*;
import java.io.IOException;
import java.util.Iterator;

@Component(immediate = true, metatype = true, label = "Cookbook Filter Service")
@Service(value = javax.servlet.Filter.class)
@Properties({ @Property(name = "filter.scope", label = "scope", value = "REQUEST"),
    @Property(name = "filter.order", label = "order", value = "1") })
public class CookbookServletFilter implements javax.servlet.Filter {
    static final Logger LOG = LoggerFactory.getLogger(CookbookServletFilter.class);

    @Reference
    private SlingSettingsService slingSettingsService;

    @Override public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        if (request instanceof SlingHttpServletRequest
            && response instanceof SlingHttpServletResponse) {
            SlingHttpServletRequest httpServletRequest = (SlingHttpServletRequest) request;
            SlingHttpServletResponse httpServletResponse = (SlingHttpServletResponse) response;

            String path = httpServletRequest.getRequestURI();

            if (slingSettingsService.getRunModes().contains("publish") &&
                path.contains("my-cookbook"))
            {
                LOG.info("**** Filter this URL ****");
                verifyUserAccess(httpServletRequest, httpServletResponse);
            }

            chain.doFilter(httpServletRequest, httpServletResponse);
        }

    }

    private void verifyUserAccess(SlingHttpServletRequest httpServletRequest, SlingHttpServletResponse httpServletResponse) {
        try {

            ResourceResolver resourceResolver = httpServletRequest.getResourceResolver();
            Session session = httpServletRequest.getResourceResolver().adaptTo(Session.class);
            UserManager userManager = resourceResolver.adaptTo(UserManager.class);
            Authorizable auth = userManager.getAuthorizable(session.getUserID());
            LOG.info("User:" + session.getUserID());
            Iterator<Group> groups = auth.memberOf();

            while (groups.hasNext()) {
                Group group = groups.next();
                LOG.info("Group:"+ group.getID());
            }

        } catch (RepositoryException e) {
            LOG.info(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override public void destroy() {

    }
}
