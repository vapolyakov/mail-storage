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

    public static String getDate() {
        return Instant.now().toString(DATE_FORMATTER);
    }

    public static String getDate(Instant instant) {
        return instant.toString(DATE_FORMATTER);
    }
}
