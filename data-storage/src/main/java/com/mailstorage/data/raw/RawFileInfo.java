package com.mailstorage.data.raw;

import java.io.File;

/**
 * @author metal
 */
public class RawFileInfo {
    private final String userId;
    private final String initialFileName;
    private final File data;

    public RawFileInfo(String userId, String initialFileName, File data) {
        this.userId = userId;
        this.initialFileName = initialFileName;
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public String getInitialFileName() {
        return initialFileName;
    }

    public File getData() {
        return data;
    }
}
