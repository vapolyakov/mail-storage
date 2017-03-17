package com.mailstorage.utils.file;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author metal
 *
 * Helps to construct unique file names and paths based on existing parts.
 */
public class FileNameUtils {
    /**
     * Generates random file name that ends with specified suffix (can be empty).
     * @param fileSuffix file name suffix to end with
     * @return random unique file name
     */
    public static String getRandomName(String fileSuffix) {
        return RandomStringUtils.randomAlphanumeric(8) + "-" + fileSuffix;
    }

    /**
     * Generates random file path for existing folder and with specified file name suffix (can be empty).
     * @param folder existing folder to put new file
     * @param fileSuffix file name suffix to end with
     * @return random file path (relative or absolute) with file in specified folder and with specified file name suffix
     */
    public static String getRandomPath(String folder, String fileSuffix) {
        return folder + "/" + getRandomName(fileSuffix);
    }
}
