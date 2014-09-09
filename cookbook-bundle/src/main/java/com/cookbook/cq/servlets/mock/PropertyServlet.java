package com.cookbook.cq.servlets.mock;

import com.cookbook.cq.service.AemPropertyService;
import com.cookbook.cq.utilities.JsonUtil;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Mock web service endpoint for locations.
 *
 */
@SlingServlet(paths = { "/services/mock/properties" })
public class PropertyServlet extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1413550339759751602L;
	private static final Logger LOG = LoggerFactory
			.getLogger(PropertyServlet.class);
	
	@Reference
	private AemPropertyService propertyService;

	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws IOException {
		LOG.info("...doGet");
		performRequest(request, response);
	}

	@Override
	protected void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		LOG.debug("...doPost");
		performRequest(request, response);
		LOG.debug("...completed!");
	}

	private void performRequest(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		Map<String,String> props = new HashMap<String,String>();
		
		//props.put("StoreEndpoint", propertyService.getStoreEndpoint());
		//props.put("Environment", propertyService.getEnvironment());
		//props.put("StorebaseUrl", propertyService.getStorebaseUrl());
		
		response.getWriter().write(JsonUtil.convert(props));

	}
	
}
