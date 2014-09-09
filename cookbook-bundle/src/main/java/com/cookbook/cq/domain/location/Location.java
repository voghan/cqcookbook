package com.cookbook.cq.domain.location;

import com.cookbook.cq.utilities.JsonUtil;

import java.io.Serializable;
import java.util.List;

public class Location implements Serializable {
	private static final long serialVersionUID = 9182089464283860388L;
	
	private String storeId;
	private String address;
    private String city;
    private String state;
    private String phone;
    private List<String> hours;
    private String path;
    
    public Location() {}
    
    public Location(String id, String address, String city, String state, String phone, List<String> hours) {
        this.storeId = id;
    	this.address = address;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.hours = hours;
    }
    
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getPhone() {
		return phone;
	}
	public List<String> getHours() {
		return hours;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setHours(List<String> hours) {
		this.hours = hours;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getTagId() {
		String tagId = "cookbook:stores/"+state+"/"
				+ city + "/" 
				+ storeId;
		return tagId.toLowerCase().replace(" ", "-").replace(".", "");
	}
	
	@Override
    public String toString() {
        return JsonUtil.convert(this);
    }
}
