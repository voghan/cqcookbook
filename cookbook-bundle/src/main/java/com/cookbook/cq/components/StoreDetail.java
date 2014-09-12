package com.cookbook.cq.components;

import com.adobe.cq.sightly.WCMUse;
import com.cookbook.cq.domain.location.Location;
import com.cookbook.cq.service.StoreLocatorService;
import com.day.cq.tagging.Tag;

/**
 * User: bvaughn
 * Date: 9/12/14
 */
public class StoreDetail extends WCMUse {

    private Location location;

    @Override public void activate() throws Exception {

        StoreLocatorService service = getSlingScriptHelper().getService(StoreLocatorService.class);

        Tag[] tags = getCurrentPage().getTags();

        Tag tag = null;
        for (int i =0; i < tags.length; i++) {

            Tag tmp = tags[i];
            if (tmp.getTagID().startsWith("cookbook:stores/")) {
                tag = tmp;
            }
        }

        String storeId = tag.getName();
        this.location = service.getLocation(storeId);

        if (location == null) {
            location = new Location();
        }
    }

    public Location getLocation() {
        return location;
    }
}
