package com.cookbook.cq.servlets;

import com.cookbook.cq.domain.Profile;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Servlet handles request to path specified and returns a JSON response.
 *
 */
@SlingServlet(paths = { "/services/customstore/profile" })
public class ProfileServlet extends AbstractServlet {

	private static final long serialVersionUID = -6609349422112586373L;
	private static final Logger LOG = LoggerFactory
			.getLogger(ProfileServlet.class);
	
	@Override
	protected void performRequest(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws IOException {
		LOG.info("...performRerquest");
		response.setContentType("application/json");
		
		Profile profile = new Profile();
		profile.setAge("26");
		profile.setGender("male");
		profile.setPreferredGenre("crime");
		
		response.getWriter().write(profile.toString());

	}

}
