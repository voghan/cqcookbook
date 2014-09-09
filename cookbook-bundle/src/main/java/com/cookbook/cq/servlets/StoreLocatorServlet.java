package com.cookbook.cq.servlets;

import com.cookbook.cq.domain.JsonMessage;
import com.cookbook.cq.domain.location.Location;
import com.cookbook.cq.service.StoreLocatorService;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Servlet handles request to path specified and returns a JSON response.
 *
 */
@SlingServlet(paths = { "/services/cookbook/stores" })
public class StoreLocatorServlet extends AbstractServlet {
	private static final long serialVersionUID = -4930645683081628144L;
	private static final Logger LOG = LoggerFactory
			.getLogger(StoreLocatorServlet.class);
	
	@Reference
	private StoreLocatorService locatorService;

	protected void performRequest(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws IOException {
		response.setContentType("application/json");

		JsonMessage<List<Location>> message = new JsonMessage<List<Location>>(
				SlingHttpServletResponse.SC_OK,
				request.getRequestParameterMap());
		try {
			
			String query = getValidParameter(request, "search");
			LOG.info("...search post:" + query);
			
			if (query == null) {
				query="all";
			}
			
			List<Location> locations = locatorService.searchLocations(query);
			LOG.info("Locations found:" + locations);
			
			message.setData(locations);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			message.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			message.setMessage(e.getMessage());
		}
		
		response.getWriter().write(message.toString());
	}

}
