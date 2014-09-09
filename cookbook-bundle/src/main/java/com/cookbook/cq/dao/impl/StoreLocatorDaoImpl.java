package com.cookbook.cq.dao.impl;

import com.cookbook.cq.dao.StoreLocatorDao;
import com.cookbook.cq.domain.location.LocationRequest;
import com.cookbook.cq.domain.location.LocationResponse;
import com.cookbook.cq.utilities.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StoreLocatorDaoImpl implements StoreLocatorDao {
	private static final Logger LOG = LoggerFactory
			.getLogger(StoreLocatorDaoImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	//Break into properties and inject them using properties
	//private static final String BASE_URL = "http://localhost:4502/services/mock/locations";

	@Value("${ws.store.baseUrl}")
	private String baseUrl;
	
	@Value("${ws.store.endpoint}")
	private String endpoint;
	/**
	 * Method will make a Rest get request based on the request.
	 * 
	 * Returns a request object that is modeled off the JSON returned.
	 * 
	 * Request/Response objects could be used to cache service calls
	 * via ehcache.
	 */
	public LocationResponse searchLocations(LocationRequest request) {
		String url = baseUrl + endpoint + "?search=" + request.getSearch();
		
		LocationResponse response = restTemplate.getForObject(url, LocationResponse.class);
		
		if (response.getLocations() == null) {
			Object tmp = restTemplate.getForObject(url, Object.class);
			LOG.info(JsonUtil.convert(tmp));
		}
		return response;
	}
	
	public LocationResponse findLocation(LocationRequest request) {
		String url = baseUrl + endpoint  +  "?storeId=" + request.getStoreId();
		
		LocationResponse response = restTemplate.getForObject(url, LocationResponse.class);
		
		if (response.getLocations() == null) {
			Object tmp = restTemplate.getForObject(url, Object.class);
			LOG.info(JsonUtil.convert(tmp));
		}
		return response;
	}

}
