package com.cookbook.cq.service.impl;

import com.cookbook.cq.dao.StoreLocatorDao;
import com.cookbook.cq.domain.location.Location;
import com.cookbook.cq.domain.location.LocationRequest;
import com.cookbook.cq.domain.location.LocationResponse;
import com.cookbook.cq.service.StoreLocatorService;
import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.TagManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * OSGi service implementation that can be accessed from within AEM.
 * 
 * This is a Spring OSGi service and is registered in the 
 * app-context-osgi.xml config file.
 */
@Service(value = "storeLocatorService")
public class StoreLocatorServiceImpl implements StoreLocatorService {
	private static final Logger LOG = LoggerFactory.getLogger(StoreLocatorServiceImpl.class);

	@Autowired
	private StoreLocatorDao storeLocatorDao;
	
	@Autowired
	private ResourceResolverFactory resolverFactory;
	
	public Location getLocation(String storeId) {
		Location location = null;
		LocationRequest request = new LocationRequest();
		request.setStoreId(storeId);
		LOG.info("..get location for " + storeId);
		
		LocationResponse response = storeLocatorDao.findLocation(request);
		
		if ( response.getLocations() != null && response.getLocations().size() > 0) {
			location = response.getLocations().get(0);
		}
		
		return location;
	}
	
	/**
	 * Service method that calls the storeLocatorDoa to find
	 * store locations.
	 * 
	 * This method is a fascade for the Rest web service that builds the
	 * request object and handles the response object from the dao.
	 */
	public List<Location> searchLocations(String search) {
		List<Location> locations = new ArrayList<Location>();
		LocationRequest request = new LocationRequest(search);
		
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
		
			LocationResponse response = storeLocatorDao.searchLocations(request);
			
			for(Location location : response.getLocations()) {
				addPath(resourceResolver, location);
				locations.add(location);
			}
		} catch (LoginException e) {
			LOG.info("Login failed and I don't care.", e);
		} finally {
			if ( resourceResolver != null && resourceResolver.isLive()) {
				resourceResolver.close();
			}
		}
		return locations;
	}
	
	private void addPath(ResourceResolver resourceResolver, Location location) {
		
		TagManager tagManager = resourceResolver.adaptTo(TagManager.class); 
		
		String tagId = location.getTagId();

		RangeIterator<Resource> resources = tagManager.find(tagId);
		if ( resources != null && resources.getSize() > 0 ) {
			Resource resource = resources.next();
			
			//Grab the page path, not resource path
			location.setPath(resource.getParent().getPath());
		}
	}

}
