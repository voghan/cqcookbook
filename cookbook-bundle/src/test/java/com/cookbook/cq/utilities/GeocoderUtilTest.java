package com.cookbook.cq.utilities;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * User: bvaughn
 * Date: 6/16/14
 */
public class GeocoderUtilTest {
    private static final Logger LOG = LoggerFactory.getLogger(GeocoderUtilTest.class);

    @Test
    public void geocode () {
        try {
            final Geocoder geocoder = new Geocoder();
            GeocoderRequest geocoderRequest =
                new GeocoderRequestBuilder().setAddress("Paris, France").setLanguage("en")
                    .getGeocoderRequest();
            GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
            assertNotNull("Response is null", geocoderResponse);
            LOG.info("Response status:" + geocoderResponse.getStatus());
            assertNotNull("results are null:" + geocoderResponse.getResults());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }
}
