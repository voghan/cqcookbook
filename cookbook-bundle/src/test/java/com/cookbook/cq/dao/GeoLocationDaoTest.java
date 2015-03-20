package com.cookbook.cq.dao;

import com.cookbook.cq.AbstractSpringAwareTest;
import com.cookbook.cq.domain.GeoLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bvaughn on 3/20/15.
 */
public class GeoLocationDaoTest extends AbstractSpringAwareTest {
    private static final Logger LOG = LoggerFactory.getLogger(GeoLocationDaoTest.class);

    public void testGeoLocation() {
        try {
            GeolocationDao dao = getApplicationContext().getBean(GeolocationDao.class);

            GeoLocation geoLocation = dao.findByIPAddress("207.250.25.162");

            assertNotNull(geoLocation);
            assertNotNull(geoLocation.getLatitude());
            assertNotNull(geoLocation.getLongitude());
            assertNotNull(geoLocation.getCity());
            assertNotNull(geoLocation.getRegion());
            assertNotNull(geoLocation.getRegionCode());
            assertNotNull(geoLocation.getCountryCode());

            assertEquals("Minneapolis", geoLocation.getCity());
            assertEquals("Minnesota", geoLocation.getRegion());
            assertEquals("MN", geoLocation.getRegionCode());
            assertEquals("US", geoLocation.getCountryCode());


        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }
}
