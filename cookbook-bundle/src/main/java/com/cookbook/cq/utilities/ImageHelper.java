package com.cookbook.cq.utilities;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bvaughn
 * Date: 7/23/14
 */
public class ImageHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ImageHelper.class);

    public static String getImagePath(String path, ResourceResolver resourceResolver, boolean webFriendly) {
        String result = path;
        Resource resource = resourceResolver.resolve(path);

        Asset image = resource.adaptTo(Asset.class);

        if (image != null && webFriendly) {

            Rendition webRendition = image.getRendition(new MyRenditionPicker());

            result = webRendition.getPath();
        }

        return result;
    }

    public static String getImagePath(String path, ResourceResolver resourceResolver) {

        return getImagePath(path, resourceResolver, true);
    }
}
