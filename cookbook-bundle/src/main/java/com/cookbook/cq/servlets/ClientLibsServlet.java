package com.cookbook.cq.servlets;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Servlet deletes the cached client libs on the server
 *
 */
@SlingServlet(paths = {"/bin/clearClientLibs"})
public class ClientLibsServlet extends AbstractServlet {
	private static final long serialVersionUID = -6192719244539588614L;
	private static final Logger LOG = LoggerFactory
			.getLogger(ClientLibsServlet.class);
	private static final String LIBS_PATH = "/var/clientlibs/etc/designs/cookbook";


	protected void performRequest(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws IOException {
		
		LOG.info("..perform request");

		Writer responseWriter = response.getWriter();
	    PrintWriter writer = new PrintWriter(responseWriter, true);
	    try {

	      ResourceResolver resolver = request.getResourceResolver();
	      Session session = resolver.adaptTo(Session.class);
	      writer.println("Deleting clientlibs...");

	      Resource clientlib = resolver.getResource(LIBS_PATH);
	      Node n = clientlib.adaptTo(Node.class);
	      n.remove();
	      session.save();
	      writer.println("\tDeleted " + clientlib.getPath() + "!");

	      writer.println("Done!");
	    } catch (Exception e) {
	      LOG.error("Error deleting clientlibs", e);
	    }
	}
}
