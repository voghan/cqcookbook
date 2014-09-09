package com.cookbook.cq.dao;

import com.cookbook.cq.domain.Author;
import com.day.cq.dam.api.Asset;
import com.day.cq.tagging.Tag;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

public interface AuthorDao {

	List<Author> findAuthors(ResourceResolver resourceResolver, Tag tag);
	
	Asset getAuthorImage(ResourceResolver resourceResolver, Tag tag);
}
