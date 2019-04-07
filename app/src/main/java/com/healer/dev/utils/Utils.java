package com.healer.dev.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private Utils() {
        // Utility class. Not for initialization
    }

    public static String getDisplayDate(long timeInSeconds) {
        try {
            timeInSeconds = timeInSeconds * 1000;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date(timeInSeconds);
            return df.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NA";
    }

}
