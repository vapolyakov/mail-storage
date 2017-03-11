package com.mailstorage.utils.local;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.util.function.Consumer;

/**
 * @author metal
 *
 * LocalFileManager provides capabilities to get unique absolute tmp path for incoming files
 * and to apply some processing functions on them with proper tmp file deletion in the end.
 */
public class LocalFileManager {
    private final File rootFolder;

    /**
     * Create LocalFileManager that stores all file in a given rootFolder.
     *
     * @param rootFolder base folder for incoming files
     */
    public LocalFileManager(String rootFolder) {
        this.rootFolder = FileUtils.getFile(rootFolder);
        this.rootFolder.mkdirs();
    }

    /**
     * Get local file for incoming data and create all necessary subfolders.
     * It's final path will look like: rootFolder/folderPrefix/{random}-fileSuffix.
     *
     * @param folderPrefix where to put file inside rootFolder, can be empty
     * @param fileSuffix filename base with extension, can be empty
     * @return file with unique absolute path
     */
    public File getFileUploadTo(String folderPrefix, String fileSuffix) {
        File newFileDir = FileUtils.getFile(rootFolder, folderPrefix);
        newFileDir.mkdirs();
        return FileUtils.getFile(newFileDir, RandomStringUtils.randomAlphanumeric(8) + "-" + fileSuffix);
    }

    /**
     * Applies {@code fileConsumer} to a new file with LocalFileManager generated path and deletes it after completion.
     *
     * @param folderPrefix where to put file inside rootFolder, can be empty
     * @param fileSuffix filename base with extension, can be empty
     * @param fileConsumer processing function that needs tmp file
     */
    public void withFile(String folderPrefix, String fileSuffix, Consumer<File> fileConsumer) {
        withFile(getFileUploadTo(folderPrefix, fileSuffix), fileConsumer);
    }

    /**
     * Applies {@code fileConsumer} to a {@code file} and deletes it after completion.
     *
     * @param fileConsumer processing function that needs file
     */
    public void withFile(File file, Consumer<File> fileConsumer) {
        try {
            fileConsumer.accept(file);
        } finally {
            FileUtils.deleteQuietly(file);
        }
    }

    File getRootFolder() {
        return rootFolder;
    }
}
