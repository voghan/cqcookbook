package com.cookbook.cq.service;

import com.cookbook.cq.domain.Author;
import com.cookbook.cq.domain.Book;
import com.day.cq.dam.api.Asset;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

public interface CookbookService {
	
	List<Book> findBooks(ResourceResolver resourceResolver, Tag tag);
	
	Asset getAuthorImage(ResourceResolver resourceResolver, Tag tag);
	
	List<Book> findBooks(ResourceResolver resourceResolver, List<Tag> tags);
	
	Author getAuthor(ResourceResolver resourceResolver, Page page);
}
