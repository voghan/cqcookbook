package com.cookbook.cq.utilities;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.api.RenditionPicker;

import java.util.List;


/**
 * User: bvaughn
 * Date: 7/23/14
 */
public class MyRenditionPicker implements RenditionPicker {

    @Override public Rendition getRendition(Asset asset) {
        List<Rendition> renditions = asset.getRenditions();
        for (Rendition rendition : renditions) {
            if (rendition.getName().startsWith("cq5dam.web.")) {
                return rendition;
            }
        }

        return asset.getOriginal();
    }
}
