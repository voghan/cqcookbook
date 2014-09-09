package com.cookbook.cq.domain.location;

import java.io.Serializable;

public class LocationRequest implements Serializable {
	private static final long serialVersionUID = -9201275961784897685L;
	private String search;
	private String storeId;

	public LocationRequest() { }
	
	public LocationRequest(String search) {
		super();
		this.search = search;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	
	
}
