package com.cookbook.cq.domain.location;

import java.io.Serializable;
import java.util.List;

public class LocationResponse implements Serializable {
	private static final long serialVersionUID = 3048692880151598035L;
	
	private List<Location> locations;

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

}
