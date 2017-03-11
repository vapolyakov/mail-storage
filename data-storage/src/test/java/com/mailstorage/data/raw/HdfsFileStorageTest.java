package com.mailstorage.data.raw;

import com.mailstorage.data.DataStorageTestData;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author metal
 */
@RunWith(SpringRunner.class)
@PropertySource("classpath:data-storage.properties")
@ContextConfiguration(classes = HdfsFileStorageTest.class)
public class HdfsFileStorageTest {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${mail.storage.hdfs.uri}")
    private String hdfsUri;
    @Value("${mail.storage.hdfs.file.storage.root.path}")
    private String rootPath;

    private HdfsFileStorage hdfsFileStorage;
    private HdfsFileStorageDaoImpl hdfsFileStorageDao;

    private File fileToUpload = new File("fileToUpload");
    private File fileToDownloadTo = new File("fileToDownloadTo");

    @Before
    public void initialize() throws IOException, URISyntaxException {
        hdfsFileStorageDao = new HdfsFileStorageDaoImpl(hdfsUri, rootPath);
        hdfsFileStorage = new HdfsFileStorage(hdfsFileStorageDao);
    }

    @Test
    public void testHdfsUploadAndDownload() throws IOException {
        FileUtils.writeStringToFile(fileToUpload, DataStorageTestData.MESSAGE, "UTF-8");
        RawFileInfo info = new RawFileInfo(DataStorageTestData.USER_ID, DataStorageTestData.FILE_NAME, fileToUpload);

        String hdfsPath = hdfsFileStorage.save(info);

        Assert.assertTrue(hdfsPath.startsWith(DataStorageTestData.USER_ID));
        Assert.assertTrue(hdfsPath.endsWith(DataStorageTestData.FILE_NAME));

        hdfsFileStorage.get(hdfsPath, fileToDownloadTo);

        Assert.assertEquals(DataStorageTestData.MESSAGE, FileUtils.readFileToString(fileToDownloadTo, "UTF-8"));
    }

    @After
    public void clean() throws IOException {
        FileUtils.deleteQuietly(fileToUpload);
        FileUtils.deleteQuietly(fileToDownloadTo);
        hdfsFileStorageDao.clean();
    }
}
