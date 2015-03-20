package com.cookbook.cq.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bvaughn on 3/20/15.
 */
public class GeoLocation extends Base {

    private String longitude;
    private String latitude;
    private String city;
    private String region;
    @SerializedName("country_code")
    private String countryCode;
    @SerializedName("region_code")
    private String regionCode;
    @SerializedName("postal_code")
    private String postalCode;


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
