package com.cookbook.cq.dao.impl;

import com.cookbook.cq.dao.BookDao;
import com.cookbook.cq.domain.Book;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Data Access Object for querying Book pages inside AEM.
 *
 */
public class BookDaoImpl extends QueryBuilderDao implements BookDao {
	private static final Logger LOG = LoggerFactory
			.getLogger(BookDaoImpl.class);

	/**
	 * Retrieve a list of Book objects based on page data.
	 */
	public List<Book> findBooks(ResourceResolver resourceResolver, Tag tag) {
		List<Book> books = new ArrayList<Book>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("path", "/content/cookbook/en/books");
		map.put("type", "cq:Page");
		map.put("tagid", tag.getTagID());
		map.put("tagid.property", "jcr:content/cq:tags");
		map.put("p.limit","-1");

		SearchResult results = search(resourceResolver, map);

		Iterator<Resource> resources = results.getResources();
		if (resources != null) {
			LOG.info("...found books");

			while (resources.hasNext()) {
				Resource resource = resources.next();

				Book book = resource.adaptTo(Book.class);
				if (book != null) {
					books.add(book);
				}
			}
		}

		return books;
	}

	/**
	 * Retrieve a list of Book objects based on page data.
	 * 
	 * Uses multiple tags in a AND type search.
	 */
	public List<Book> findBooks(ResourceResolver resourceResolver, List<Tag> tags) {
		List<Book> books = new ArrayList<Book>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("path", "/content/cookbook/en/books");
		map.put("type", "cq:Page");

		int i = 0;
		for (Tag tag : tags) {
			i++;
			map.put(i + "_property.value", tag.getTagID() + "%");
			map.put(i + "_property", "jcr:content/cq:tags");
			map.put(i + "_property.operation", "like");
		}
		map.put("p.limit","-1");
		map.put("orderby","@jcr:content/par/bookdetail/title");
		map.put("orderby.sort","asc");

		SearchResult results = search(resourceResolver, map);
		
		if (true) {
			LOG.info(results.getQueryStatement());
		}

		Iterator<Resource> resources = results.getResources();
		if (resources != null) {
			LOG.info("...found books");

			while (resources.hasNext()) {
				Resource resource = resources.next();

				Book book = resource.adaptTo(Book.class);
				if (book != null) {
					books.add(book);
				}
			}
		}

		return books;
	}

}
