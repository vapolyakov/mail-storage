package com.mailstorage.utils.file;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author metal
 */
public class FileNameUtils {
    public static String getRandomName(String fileSuffix) {
        return RandomStringUtils.randomAlphanumeric(8) + "-" + fileSuffix;
    }

    public static String getRandomPath(String folder, String fileSuffix) {
        return folder + "/" + getRandomName(fileSuffix);
    }
}
