package com.mailstorage.data.raw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author metal
 */
@Configuration
@PropertySource("classpath:data-storage.properties")
public class HdfsConfiguration {
    @Bean
    public HdfsFileStorageDaoImpl hdfsFileStorageDaoImpl(
            @Value("${mail.storage.hdfs.uri}")
            String hdfsUri,
            @Value("${mail.storage.hdfs.file.storage.root.path}")
            String rootPath) throws IOException, URISyntaxException
    {
        return new HdfsFileStorageDaoImpl(hdfsUri, rootPath);
    }

    @Bean
    public HdfsFileStorage hdfsFileStorage(HdfsFileStorageDaoImpl rawFileStorageDao) {
        return new HdfsFileStorage(rawFileStorageDao);
    }
}
