package com.tads.mhsf.bazaar.util;

import java.time.LocalTime;

public class TimeUtils {
    public static LocalTime convertElapsedMinutesToLocalTime(int elapsedMinutes) {
        int hours = elapsedMinutes / 60;
        int minutes = elapsedMinutes % 60;
        return LocalTime.of(hours, minutes);
    }

    public static int convertLocalTimeToElapsedMinutes(LocalTime localTime) {
        return localTime.getHour() * 60 + localTime.getMinute();
    }
}
