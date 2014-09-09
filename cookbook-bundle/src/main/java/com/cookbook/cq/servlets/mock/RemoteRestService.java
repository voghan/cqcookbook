package com.cookbook.cq.servlets.mock;

import com.cookbook.cq.domain.location.Location;
import com.cookbook.cq.domain.location.LocationResponse;
import com.cookbook.cq.utilities.JsonUtil;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock web service endpoint for locations.
 *
 */
@SlingServlet(paths = { "/services/mock/locations" })
public class RemoteRestService extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1413550339759751602L;
	private static final Logger LOG = LoggerFactory
			.getLogger(RemoteRestService.class);
	
	private Map<String,Location> storeLocations = new HashMap<String, Location>();
	
	public RemoteRestService () {
		buildMap();
	}

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
		
		String id = request.getParameter("storeId");
		String search = request.getParameter("search");
		LOG.info("...store id is " + id);
		LOG.info("...search is " + search);
		
		LocationResponse locationResponse = new LocationResponse();
		List<Location> locations = new ArrayList<Location>();
		
		if (id == null || id.trim().length() == 0 ) {
			LOG.info("...performing search");
			locations = new ArrayList<Location>(storeLocations.values());
			locationResponse.setLocations(locations);
		} else {
			LOG.info("...performing store lookup");
			Location location = storeLocations.get(id);
			locations.add(location);
		}
		locationResponse.setLocations(locations);
		
		response.getWriter().write(JsonUtil.convert(locationResponse));

	}
	
	private void buildMap() {
		
		List<String> hours = new ArrayList<String>();
        hours.add("Sun 10:00AM-8:00PM");
        hours.add("Mon-SAT 9:00AM-10:00PM");

        storeLocations.put("1001", new Location("1001", "2040 Wedgewood Lane", "Plymouth", "MN", "763-804-1700",hours));
        storeLocations.put("1002", new Location("1002","765 Snow Lane", "St. Paul", "MN", "763-844-1760",hours));
        storeLocations.put("1003", new Location("1003","12233 Stark Ave", "Hopkins", "MN", "763-854-7700",hours));
        storeLocations.put("1004", new Location("1004","7898 Lanister Lane", "Eagan", "MN", "763-894-1100",hours));
        storeLocations.put("1005", new Location("1005","1120 Dorn St", "Bloomington", "MN", "763-974-1500",hours));
        storeLocations.put("1006", new Location("1006","3001 Highgarden Ave", "Dearborn", "MI", "313-834-1700",hours));
        storeLocations.put("1007", new Location("1007","3400 Bravos St", "Flynt", "MI", "313-844-1740",hours));
        storeLocations.put("1008", new Location("1008","7500 Wedgewood Ave", "Highland Park", "MI", "313-884-5704",hours));
        storeLocations.put("1009", new Location("1009","3232 Wedgewood Dr", "Allen Park", "MI", "313-454-5740",hours));
	}
}
