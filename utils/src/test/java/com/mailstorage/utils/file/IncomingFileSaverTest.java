package com.mailstorage.utils.file;

import com.mailstorage.utils.UtilsTestData;
import com.mailstorage.utils.timestamp.CurrentDateProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author metal
 */
public class IncomingFileSaverTest {
    private LocalFileManager localFileManager;
    private IncomingFileSaver incomingFileSaver;

    @Before
    public void initialize() {
        localFileManager = new LocalFileManager(UtilsTestData.TEST_FOLDER);
        incomingFileSaver = new IncomingFileSaver(localFileManager);
    }

    @Test
    public void testFileSaving() throws IOException {
        File saved = incomingFileSaver.saveIncomingFileLocally(
                UtilsTestData.USER_ID, UtilsTestData.FILE_NAME, IOUtils.toInputStream("test message", "UTF-8"));

        Assert.assertTrue(saved.getAbsolutePath().contains(constructPathPart()));
        Assert.assertTrue(saved.getAbsolutePath().endsWith(UtilsTestData.FILE_NAME));
    }

    @After
    public void clean() throws IOException {
        FileUtils.deleteDirectory(localFileManager.getRootFolder());
    }

    private String constructPathPart() {
        return CurrentDateProvider.getDate() + "/" + UtilsTestData.USER_ID;
    }
}
