package com.cookbook.cq.dao.impl;

import com.cookbook.cq.dao.GeolocationDao;
import com.cookbook.cq.domain.GeoLocation;
import com.cookbook.cq.utilities.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GeolocationDaoImpl implements GeolocationDao {
    private static final Logger LOG = LoggerFactory.getLogger(GeolocationDaoImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    //Break into properties and inject them using properties
    //private static final String BASE_URL = "http://localhost:4502/services/mock/locations";

    @Value("${ws.geolocation.baseUrl}")
    private String baseUrl;

    @Value("${ws.geolocation.endpoint}")
    private String endpoint;

    @Override
    public GeoLocation findByIPAddress(String ipAddress) {
        String url = baseUrl + endpoint + ipAddress;

        GeoLocation response = restTemplate.getForObject(url, GeoLocation.class);

        if (response == null) {
            Object tmp = restTemplate.getForObject(url, Object.class);
            LOG.info(JsonUtil.convert(tmp));
        }
        return response;
    }
}
