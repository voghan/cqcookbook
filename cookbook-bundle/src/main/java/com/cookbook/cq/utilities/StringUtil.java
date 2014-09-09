package com.cookbook.cq.utilities;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bvaughn
 * Date: 8/4/14
 */
public class StringUtil {
    static final Logger LOG = LoggerFactory.getLogger(StringUtil.class);

    public static boolean isUnassigned(String value) {
        boolean empty = false;

        if (StringUtils.isBlank(value)) {
            empty = true;
        } else if (value.equals("null")) {
            empty = true;
        }

        return empty;
    }
}
