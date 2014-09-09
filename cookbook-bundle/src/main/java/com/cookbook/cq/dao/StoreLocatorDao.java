package com.cookbook.cq.dao;

import com.cookbook.cq.domain.location.LocationRequest;
import com.cookbook.cq.domain.location.LocationResponse;

public interface StoreLocatorDao {

	LocationResponse searchLocations(LocationRequest request);
	
	LocationResponse findLocation(LocationRequest request);
}
