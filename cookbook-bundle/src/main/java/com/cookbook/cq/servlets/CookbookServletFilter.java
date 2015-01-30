package com.cookbook.cq.servlets;

import com.cookbook.cq.domain.Profile;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

@Component(immediate = true, metatype = true, label = "Cookbook Filter Service")
@Service(value = javax.servlet.Filter.class)
@Properties({ @Property(name = "filter.scope", label = "scope", value = "REQUEST"),
    @Property(name = "filter.order", label = "order", value = "1") })
public class CookbookServletFilter implements javax.servlet.Filter {
    static final Logger LOG = LoggerFactory.getLogger(CookbookServletFilter.class);

    @Reference
    private SlingSettingsService slingSettingsService;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        if (request instanceof SlingHttpServletRequest
            && response instanceof SlingHttpServletResponse) {
            SlingHttpServletRequest httpServletRequest = (SlingHttpServletRequest) request;
            SlingHttpServletResponse httpServletResponse = (SlingHttpServletResponse) response;

            String path = httpServletRequest.getRequestURI();
            boolean handled = false;

            //Only run on publisher (no need to filter authors)
            //Check if path matches the secure path
            if (slingSettingsService.getRunModes().contains("publish") &&
                path.contains("secure-path"))
            {
                handled = verifyUserAccess(httpServletRequest, httpServletResponse);
            }

            //only doFilter if not handled
            if (!handled) {
                chain.doFilter(httpServletRequest, httpServletResponse);
            }
        }

    }

    private boolean verifyUserAccess(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        boolean handled = false;
        try {
            //find user session object
            Profile authenticationInfo =
                (Profile) request.getSession().getAttribute(Profile.SESSION_KEY);

            //if null forward to login screen
            if (authenticationInfo == null) {
                String path = request.getRequestURI();
                String redirect = "/login";

                //pass resource as attribute for login component to read
                // and pass on upon successful authentication
                request.setAttribute("resource",path);
                request.getRequestDispatcher(redirect).forward(request, response);
                handled = true;
            }

        } catch (ServletException e) {
            LOG.warn(e.getMessage(), e);
        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
        }
        return handled;
    }

    public void destroy() {

    }
}
