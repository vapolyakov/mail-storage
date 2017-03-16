package com.mailstorage.core.artifact;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.primary.CommonPrimaryEntityManager;
import com.mailstorage.core.artifact.extractors.AttachmentCountArtifactExtractor;
import com.mailstorage.core.artifact.extractors.OrclWordArtifactExtractor;
import com.mailstorage.core.artifact.extractors.SberWordArtifactExtractor;
import com.mailstorage.core.artifact.extractors.SubjectArtifactExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.artifact.SberWordArtifact;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author metal
 */
@Configuration
public class ArtifactExtractorConfiguration {
    @Bean
    public BaseArtifactManager<OrclWordArtifact> orclWordArtifactManager(AbstractHBDAO<Long, OrclWordArtifact> dao) {
        return new BaseArtifactManager<>(new OrclWordArtifactExtractor(), dao);
    }

    @Bean
    public BaseArtifactManager<SberWordArtifact> sberWordArtifactManager(AbstractHBDAO<Long, SberWordArtifact> dao) {
        return new BaseArtifactManager<>(new SberWordArtifactExtractor(), dao);
    }

    @Bean
    public BaseArtifactManager<AttachmentCountArtifact> attachmentCountArtifactManage(AbstractHBDAO<Long, AttachmentCountArtifact> dao) {
        return new BaseArtifactManager<>(new AttachmentCountArtifactExtractor(), dao);
    }

    @Bean
    public BaseArtifactManager<SubjectArtifact> subjectArtifactManager(AbstractHBDAO<Long, SubjectArtifact> dao,
            @Value("${mail.storage.artifact.subject.suspicious.words}")
            String suspiciousWords,
            @Value("${mail.storage.artifact.subject.fraud.emails}")
            String fraudEmails)
    {
        return new BaseArtifactManager<>(new SubjectArtifactExtractor(fraudEmails, suspiciousWords), dao);
    }

    @Bean
    public CommonPrimaryEntityManager<Mail, BaseArtifactManager> commonArtifactManager(List<BaseArtifactManager> managers) {
        return new CommonPrimaryEntityManager<>(managers);
    }
}
