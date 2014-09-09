package com.cookbook.cq.dao.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Session;
import java.util.Map;

/**
 * This is a convenience class that abstracts out the boilerplate code 
 * for using QueryBuilder.
 *
 */
public abstract class QueryBuilderDao {

	/**
	 * This method will take a map of search criteria and return the search results.
	 * 
	 * @param resourceResolver
	 * @param criteria
	 * @return
	 */
	public SearchResult search(ResourceResolver resourceResolver, Map<String, String> criteria) {
		
		Session session = resourceResolver.adaptTo(Session.class);
        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);

        Query query =  queryBuilder.createQuery(PredicateGroup.create(criteria), session);

        return query.getResult();
	}
}
