package com.cookbook.cq.utilities;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

import java.util.ArrayList;
import java.util.List;

/**
 * User: bvaughn
 * Date: 6/30/14
 */
public class TagHelper {

    public static List<Tag> convertTags(String[] properties, TagManager tagManager) {
        List<Tag> tags = new ArrayList<Tag>();

        for(String property : properties) {
            Tag tag = tagManager.resolve(property);
            if ( tag != null ) {
                tags.add(tag);
            }
        }

        return tags;
    }
}
