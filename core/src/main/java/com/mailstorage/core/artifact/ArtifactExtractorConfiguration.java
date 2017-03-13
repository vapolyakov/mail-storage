package com.mailstorage.core.artifact;

import com.mailstorage.core.artifact.attachement.AttachmentCountArtifactExtractor;
import com.mailstorage.core.artifact.attachement.AttachmentCountArtifactManager;
import com.mailstorage.core.artifact.orcl.OrclWordArtifactExtractor;
import com.mailstorage.core.artifact.orcl.OrclWordArtifactManager;
import com.mailstorage.core.artifact.sber.SberWordArtifactExtractor;
import com.mailstorage.core.artifact.sber.SberWordArtifactManager;
import com.mailstorage.core.artifact.subject.SubjectArtifactExtractor;
import com.mailstorage.core.artifact.subject.SubjectArtifactManager;
import com.mailstorage.data.mail.dao.artifact.AttachmentCountArtifactDao;
import com.mailstorage.data.mail.dao.artifact.OrclWordArtifactDao;
import com.mailstorage.data.mail.dao.artifact.SberWordArtifactDao;
import com.mailstorage.data.mail.dao.artifact.SubjectArtifactDao;
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
    public OrclWordArtifactManager orclWordArtifactManager(OrclWordArtifactDao dao) {
        return new OrclWordArtifactManager(new OrclWordArtifactExtractor(), dao);
    }

    @Bean
    public SberWordArtifactManager sberWordArtifactManager(SberWordArtifactDao dao) {
        return new SberWordArtifactManager(new SberWordArtifactExtractor(), dao);
    }

    @Bean
    public AttachmentCountArtifactManager attachmentCountArtifactManage(AttachmentCountArtifactDao dao) {
        return new AttachmentCountArtifactManager(new AttachmentCountArtifactExtractor(), dao);
    }

    @Bean
    public SubjectArtifactManager subjectArtifactManager(SubjectArtifactDao dao,
            @Value("${mail.storage.artifact.subject.suspicious.words}")
            String suspiciousWords,
            @Value("${mail.storage.artifact.subject.fraud.emails}")
            String fraudEmails)
    {
        return new SubjectArtifactManager(new SubjectArtifactExtractor(fraudEmails, suspiciousWords), dao);
    }

    @Bean
    public CommonArtifactManager commonArtifactManager(List<BaseArtifactManager> managers) {
        return new CommonArtifactManager(managers);
    }
}
