package com.mailstorage.data.raw;

import java.io.File;

/**
 * @author metal
 *
 * Storage for raw incoming files without any meta data.
 */
public interface RawFileStorageDao<T> {
    /**
     * Save file from local hard drive with specific id
     *
     * @param fileToUpload file to upload
     * @param id id to associate file with
     */
    void save(File fileToUpload, T id);

    /**
     * Download specific file from storage to a local machine
     *
     * @param id file id in storage
     * @param destination local file to download to
     */
    void get(T id, File destination);
}
