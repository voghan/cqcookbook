package com.cookbook.cq.dao;

import com.cookbook.cq.domain.Book;
import com.day.cq.tagging.Tag;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

public interface BookDao {

	List<Book> findBooks(ResourceResolver resourceResolver, Tag tag);
	
	List<Book> findBooks(ResourceResolver resourceResolver, List<Tag> tags);
	
}
