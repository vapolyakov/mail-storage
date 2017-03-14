package com.mailstorage.data.mail.dao;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.DataStorageHBaseConfiguration;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.artifact.SberWordArtifact;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;
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
    public AbstractHBDAO<Long, Mail> mailDao(Configuration conf) throws IOException {
        return new AbstractHBDAO<Long, Mail>(conf) {};
    }

    @Bean
    public AbstractHBDAO<Long, OrclWordArtifact> orclWordArtifactDao(Configuration conf) throws IOException {
        return new AbstractHBDAO<Long, OrclWordArtifact>(conf) {};
    }

    @Bean
    public AbstractHBDAO<Long, SberWordArtifact> sberWordArtifactDao(Configuration conf) throws IOException {
        return new AbstractHBDAO<Long, SberWordArtifact>(conf) {};
    }

    @Bean
    public AbstractHBDAO<Long, AttachmentCountArtifact> attachmentCountArtifactDao(Configuration conf) throws IOException {
        return new AbstractHBDAO<Long, AttachmentCountArtifact>(conf) {};
    }

    @Bean
    public AbstractHBDAO<Long, SubjectArtifact> subjectArtifactDao(Configuration conf) throws IOException {
        return new AbstractHBDAO<Long, SubjectArtifact>(conf) {};
    }
}
