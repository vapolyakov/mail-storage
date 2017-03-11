package com.mailstorage.data.raw;

import java.io.File;

/**
 * @author metal
 *
 * Storage for raw incoming files that can associate file with it's owner and file name.
 */
public interface RawFileStorage<T> {
    /**
     * Save file from local hard drive in storage
     *
     * @param rawFileInfo info about local file to save
     * @return file id in storage
     */
    T save(RawFileInfo rawFileInfo);

    /**
     * Download specific file from storage to a local machine
     *
     * @param id file id in storage
     * @param destination local file to download to
     */
    void get(T id, File destination);
}
