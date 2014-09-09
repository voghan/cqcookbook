package com.cookbook.cq.servlets;

import com.cookbook.cq.domain.JsonMessage;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public abstract class AbstractServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = -5924724440978419004L;
    private static final Logger LOG = LoggerFactory.getLogger(AbstractServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
        throws IOException {

        response.setHeader("Dispatcher", "no-cache");

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

        performRequest(request, response);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
        throws ServletException, IOException {

        response.setHeader("Dispatcher", "no-cache");

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

        performRequest(request, response);
    }


    abstract protected void performRequest(SlingHttpServletRequest request,
        SlingHttpServletResponse response) throws IOException;

    protected String getValidParameter(SlingHttpServletRequest request, String field) {
        String param = request.getParameter(field);

        if (param == null || param.trim().length() == 0) {
            param = null;
        }

        return param;
    }

    protected String getValidParameter(SlingHttpServletRequest request, String field, boolean required)
        throws Exception {
        String param = request.getParameter(field);

        if (param == null || param.trim().length() == 0) {
            param = null;
        }

        if (required && param == null) {
            throw new Exception("Missing parameter:" + field);
        }

        return param;
    }

    protected int getValidIntParameter(SlingHttpServletRequest request, String field) {
        String param = request.getParameter(field);

        if (param == null || param.trim().length() == 0) {
            param = null;
        }

        return Integer.parseInt(param);
    }

    protected void processError(JsonMessage<List<Object>> message, String errorMessage, int errorCode, Throwable e) {
        message.setStatus(errorCode);
        message.setMessage(errorMessage);
        LOG.error(message.toString(), e);
    }
}
