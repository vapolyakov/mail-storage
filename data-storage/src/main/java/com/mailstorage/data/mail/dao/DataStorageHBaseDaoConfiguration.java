package com.mailstorage.data.mail.dao;

import com.mailstorage.data.mail.DataStorageHBaseConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.io.IOException;

/**
 * @author metal
 */
@org.springframework.context.annotation.Configuration
@Import({
        DataStorageHBaseConfiguration.class
})
public class DataStorageHBaseDaoConfiguration {
    @Bean
    public MailDao mailDao(Configuration conf) throws IOException {
        return new MailDao(conf);
    }

    @Bean
    public OrclWordArtifactDao orclWordArtifactDao(Configuration conf) throws IOException {
        return new OrclWordArtifactDao(conf);
    }
}
