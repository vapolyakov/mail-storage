package com.mailstorage.core;

import com.mailstorage.core.artifact.ArtifactExtractorConfiguration;
import com.mailstorage.core.artifact.CommonArtifactManager;
import com.mailstorage.core.general.GeneralEmailInformationManager;
import com.mailstorage.data.mail.dao.MailDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author metal
 */
@Configuration
@PropertySource("classpath:core.properties")
@Import({
        ArtifactExtractorConfiguration.class
})
public class CoreConfiguration {
    @Bean
    public ThreadPoolExecutor generalInformationExtractorExecutor(
            @Value("${mail.storage.general.info.extractor.threads}")
            int threads)
    {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
    }

    @Bean
    public ThreadPoolExecutor artifactExtractorExecutor(
            @Value("${mail.storage.artifact.extractor.threads}")
            int threads)
    {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
    }

    @Bean
    public GeneralEmailInformationManager generalEmailInformationManager(MailDao mailDao) {
        return new GeneralEmailInformationManager(mailDao);
    }

    @Bean
    public Stages stages(
            @Qualifier("generalInformationExtractorExecutor")
            ThreadPoolExecutor generalInformationExtractorExecutor,
            GeneralEmailInformationManager generalEmailInformationManager,
            @Qualifier("artifactExtractorExecutor")
            ThreadPoolExecutor artifactExtractorExecutor,
            CommonArtifactManager commonArtifactManager)
    {
        return new Stages(generalInformationExtractorExecutor, generalEmailInformationManager,
                artifactExtractorExecutor, commonArtifactManager);
    }
}
