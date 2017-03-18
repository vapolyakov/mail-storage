package com.mailstorage.data.mail.dao;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.flipkart.hbaseobjectmapper.HBObjectMapper;
import com.mailstorage.data.mail.DataStorageHBaseConfiguration;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.artifact.SberWordArtifact;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;
import com.mailstorage.data.mail.entities.feature.CompositeFeature;
import com.mailstorage.data.mail.entities.feature.LengthFeature;
import com.mailstorage.data.mail.entities.feature.OrclRelevanceFeature;
import com.mailstorage.data.mail.entities.feature.SberRelevanceFeature;
import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @author metal
 */
@org.springframework.context.annotation.Configuration
@PropertySource("classpath:data-storage.properties")
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

    @Bean
    public AbstractHBDAO<Long, CompositeFeature> compositeFeatureDao(Configuration conf) throws IOException {
        return new AbstractHBDAO<Long, CompositeFeature>(conf) {};
    }

    @Bean
    public AbstractHBDAO<Long, LengthFeature> lengthFeatureDao(Configuration conf) throws IOException {
        return new AbstractHBDAO<Long, LengthFeature>(conf) {};
    }

    @Bean
    public AbstractHBDAO<Long, OrclRelevanceFeature> orclRelevanceDao(Configuration conf) throws IOException {
        return new AbstractHBDAO<Long, OrclRelevanceFeature>(conf) {};
    }

    @Bean
    public AbstractHBDAO<Long, SberRelevanceFeature> sberRelevanceDao(Configuration conf) throws IOException {
        return new AbstractHBDAO<Long, SberRelevanceFeature>(conf) {};
    }

    @Bean
    public RawHBaseDao rawHBaseDao(Configuration configuration,
            @Value("${mail.storage.raw.hbase.dao.scan.caching.number}")
            int numberOfRowsForCachingInScans,
            RawHBaseDaoHelper rawHBaseDaoHelper) throws IOException
    {
        return new RawHBaseDao(configuration, numberOfRowsForCachingInScans, rawHBaseDaoHelper);
    }

    @Bean
    public HBObjectMapper hbObjectMapper() {
        return new HBObjectMapper();
    }

    @Bean
    public RawHBaseDaoHelper rawHBaseDaoHelper(HBObjectMapper hbObjectMapper) {
        return new RawHBaseDaoHelper(hbObjectMapper);
    }
}
