package com.example.bit.util;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {


    /**
     * 將 "2024-09-02T07:07:20+00:00" > 1990/01/01 00:00:00
     * @param dateTime
     * @param format
     * @return
     */
    public static String formatDateToStr(String dateTime, String format) {
        if (dateTime == null) return "";

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return offsetDateTime.format(formatter);
    }
}
