package com.cookbook.cq.service;

import com.cookbook.cq.domain.location.Location;

import java.util.List;

public interface StoreLocatorService {
	
	List<Location> searchLocations(String search);
	
	Location getLocation(String storeId);

}
