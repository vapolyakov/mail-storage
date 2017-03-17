package com.mailstorage.utils.timestamp;

import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author metal
 *
 * Allows to get current date in specific yyyy-MM-dd format.
 */
public class CurrentDateProvider {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    /**
     * Get current date string in yyyy-MM-dd format.
     * @return current date string in yyyy-MM-dd format
     */
    public static String getDate() {
        return Instant.now().toString(DATE_FORMATTER);
    }

    /**
     * Get specified datetime in yyyy-MM-dd format.
     * @param instant datetime to convert to yyyy-MM-dd string
     * @return specified datetime in yyyy-MM-dd format
     */
    public static String getDate(Instant instant) {
        return instant.toString(DATE_FORMATTER);
    }
}
