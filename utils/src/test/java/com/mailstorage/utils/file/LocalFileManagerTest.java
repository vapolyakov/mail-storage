package com.mailstorage.utils.file;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author metal
 */
public class LocalFileManagerTest {
    private static final String TEST_FOLDER = "testfolder";
    private static final String META_PATH = "2017-03-11/metal";
    private static final String FILE_NAME = "received.eml";

    private LocalFileManager fileManager;

    @Before
    public void initialize() {
        fileManager = new LocalFileManager(TEST_FOLDER);
    }

    @Test
    public void testNewFileGenerationAndProcessing() {
        File uploadedFile = fileManager.getFileUploadTo(META_PATH, FILE_NAME);

        Assert.assertTrue(uploadedFile.getAbsolutePath().endsWith(FILE_NAME));
        Assert.assertTrue(uploadedFile.getAbsolutePath().contains(TEST_FOLDER + "/" + META_PATH));

        fileManager.withFile(uploadedFile, file ->  {
            try {
                FileUtils.writeStringToFile(uploadedFile, "message", "UTF-8");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Assert.assertFalse(uploadedFile.exists());
    }

    @After
    public void clean() throws IOException {
        FileUtils.deleteDirectory(fileManager.getRootFolder());
    }
}
