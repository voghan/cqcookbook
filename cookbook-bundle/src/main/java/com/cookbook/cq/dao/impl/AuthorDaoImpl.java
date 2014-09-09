package com.cookbook.cq.dao.impl;

import com.cookbook.cq.dao.AuthorDao;
import com.cookbook.cq.domain.Author;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.*;

/**
 * Data Access Object for querying Author pages inside AEM.
 *
 */
public class AuthorDaoImpl extends QueryBuilderDao implements AuthorDao {

	/**
	 * Retrieve list of author objects based on page data.
	 */
	public List<Author> findAuthors(ResourceResolver resourceResolver, Tag tag) {
		 List<Author> authors = new ArrayList<Author>();
		
		Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/cookbook/en/authors");
        map.put("type","cq:Page");
        map.put("tagid",tag.getTagID());
        map.put("tagid.property","jcr:content/cq:tags");
        map.put("p.limit","-1");
        
        SearchResult results = search(resourceResolver, map);
        
        Iterator<Resource> resources = results.getResources();
        if (resources != null) {

            while(resources.hasNext()) {
                Resource resource = resources.next();

                Author author  = resource.adaptTo(Author.class);
                if ( author != null ) {
                	authors.add(author);
                }
            }
        }

        
		return authors;
	}
	/**
	 * Retrieve a list of author images from the dam.
	 */
	public Asset getAuthorImage(ResourceResolver resourceResolver, Tag tag) {
		Asset image = null;
		
		Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/dam/cookbook/authors");
        map.put("type","dam:Asset");
        map.put("tagid",tag.getTagID());
        map.put("tagid.property","jcr:content/metadata/cq:tags");
        
        SearchResult results = search(resourceResolver, map);
        
        Iterator<Resource> resources = results.getResources();
        if (resources != null) {
        	while(resources.hasNext()) {
                Resource resource = resources.next();

                Asset tmp  = resource.adaptTo(Asset.class);
                if ( tmp != null ) {
                	image = tmp;
                	break;
                }
            }
        }
		
		return image;
	}

}
