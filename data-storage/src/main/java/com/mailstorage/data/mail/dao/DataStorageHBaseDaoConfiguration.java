package com.mailstorage.data.mail.dao;

import com.mailstorage.data.mail.DataStorageHBaseConfiguration;
import com.mailstorage.data.mail.dao.artifact.AttachmentCountArtifactDao;
import com.mailstorage.data.mail.dao.artifact.OrclWordArtifactDao;
import com.mailstorage.data.mail.dao.artifact.SberWordArtifactDao;
import com.mailstorage.data.mail.dao.artifact.SubjectArtifactDao;
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

    @Bean
    public SberWordArtifactDao sberWordArtifactDao(Configuration conf) throws IOException {
        return new SberWordArtifactDao(conf);
    }

    @Bean
    public AttachmentCountArtifactDao attachmentCountArtifactDao(Configuration conf) throws IOException {
        return new AttachmentCountArtifactDao(conf);
    }

    @Bean
    public SubjectArtifactDao subjectArtifactDao(Configuration conf) throws IOException {
        return new SubjectArtifactDao(conf);
    }
}
