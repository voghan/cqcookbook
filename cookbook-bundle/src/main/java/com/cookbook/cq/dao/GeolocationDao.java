package com.cookbook.cq.dao;

import com.cookbook.cq.domain.GeoLocation;

/**
 * Created by bvaughn on 3/20/15.
 */
public interface GeolocationDao {

    GeoLocation findByIPAddress(String ipAddress);
}
