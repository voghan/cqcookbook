package com.cookbook.cq.servlets;

import com.cookbook.cq.domain.Book;
import com.cookbook.cq.domain.JsonMessage;
import com.cookbook.cq.service.CookbookService;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet handles request to path specified and returns a JSON response.
 *
 */
@SlingServlet(paths = { "/services/cookbook/books" })
public class BookServlet extends AbstractServlet {
	private static final long serialVersionUID = 1413550339759751602L;
	private static final Logger LOG = LoggerFactory
			.getLogger(BookServlet.class);

	@Reference
	private CookbookService cookbookService;


	protected void performRequest(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws IOException {

		response.setContentType("application/json");

		ResourceResolver resourceResolver = request.getResourceResolver();
		JsonMessage<List<Book>> message = new JsonMessage<List<Book>>(
				SlingHttpServletResponse.SC_OK,
				request.getRequestParameterMap());

		try {

			List<Tag> tags = new ArrayList<Tag>();
			
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			
			addTag(request, tags, tagManager, "authorTagId");
			addTag(request, tags, tagManager, "genreTagId");

			List<Book> books = cookbookService.findBooks(resourceResolver, tags);

			message.setData(books);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			message.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			message.setMessage(e.getMessage());
		}

		response.getWriter().write(message.toString());

	}

	private void addTag(SlingHttpServletRequest request, List<Tag> tags,
			TagManager tagManager, String param) {
		String authorTagId = getValidParameter(request, param);
		Tag authorTag = tagManager.resolve(authorTagId);
		if ( authorTag != null) {
			tags.add(authorTag);
		}
	}

}
