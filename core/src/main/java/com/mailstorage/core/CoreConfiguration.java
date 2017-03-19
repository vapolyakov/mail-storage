package com.mailstorage.core;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.artifact.ArtifactExtractorConfiguration;
import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.core.feature.primary.BaseFeatureManager;
import com.mailstorage.core.feature.primary.FeatureExtractorConfiguration;
import com.mailstorage.core.feature.secondary.SecondaryFeaturesConfiguration;
import com.mailstorage.core.feature.secondary.accumulator.EntityAccumulator;
import com.mailstorage.core.general.GeneralEmailInformationManager;
import com.mailstorage.core.primary.CommonPrimaryEntityManager;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.data.mail.entities.Mail;
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
        ArtifactExtractorConfiguration.class,
        FeatureExtractorConfiguration.class,
        SecondaryFeaturesConfiguration.class
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
    public ThreadPoolExecutor featureExtractorExecutor(
            @Value("${mail.storage.feature.extractor.threads}")
            int threads)
    {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
    }

    @Bean
    public GeneralEmailInformationManager generalEmailInformationManager(AbstractHBDAO<Long, Mail> mailDao) {
        return new GeneralEmailInformationManager(mailDao);
    }

    @Bean
    public Stages stages(
            @Qualifier("generalInformationExtractorExecutor")
            ThreadPoolExecutor generalInformationExtractorExecutor,
            @Qualifier("artifactExtractorExecutor")
            ThreadPoolExecutor artifactExtractorExecutor,
            @Qualifier("featureExtractorExecutor")
            ThreadPoolExecutor featureExtractorExecutor,
            GeneralEmailInformationManager generalEmailInformationManager,
            @Qualifier("commonArtifactManager")
            CommonPrimaryEntityManager<Mail, BaseArtifactManager> commonArtifactManager,
            @Qualifier("commonFeatureManager")
            CommonPrimaryEntityManager<PrimaryEntitiesRegistry, BaseFeatureManager> commonFeatureManager,
            EntityAccumulator entityAccumulator)
    {
        return new Stages(
                generalInformationExtractorExecutor, generalEmailInformationManager,
                artifactExtractorExecutor, commonArtifactManager,
                featureExtractorExecutor, commonFeatureManager,
                entityAccumulator);
    }
}
