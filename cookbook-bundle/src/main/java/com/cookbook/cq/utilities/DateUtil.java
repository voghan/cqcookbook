package com.cookbook.cq.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: bvaughn
 * Date: 8/25/14
 */
public class DateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
    //Java 7
    //public static final String SYSTEM_FMT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    //Java 6
    public static final String SYSTEM_FMT = "yyyy-MM-dd'T'";
    public static final String US_FMT = "MM/dd/yyyy";

    public static Date formatDate(String date, String fmt) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.parse(date);
    }

    public static String formatString(String date, String inputFmt, String outputFmt) {
        String tmp = "";
        try {
            Date tmpDate = formatDate(date, inputFmt);
            SimpleDateFormat sdf = new SimpleDateFormat(outputFmt);
            tmp = sdf.format(tmpDate);
        } catch (ParseException e) {
            LOG.warn(e.getMessage());
        }

        return tmp;
    }


}
