package com.weather.app;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yaroslav on 27-Feb-16.
 */
public class Utility {

    public static String simpleDate(long timeInSec){
        SimpleDateFormat smpTimeForm = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime(timeInSec*1000L);
        return smpTimeForm.format(date);
    }

    public static String windDirect(Double degrees){

        String direct = "";

        if (degrees == null) return direct;

        if (degrees >= 337.5 || degrees < 22.5) {
            direct = "N";
        } else if (degrees >= 292.5 && degrees < 337.5) {
            direct = "NW";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            direct = "W";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            direct = "SW";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            direct = "S";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            direct = "SE";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            direct = "E";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            direct = "NE";
        }

        return direct;
    }
}
