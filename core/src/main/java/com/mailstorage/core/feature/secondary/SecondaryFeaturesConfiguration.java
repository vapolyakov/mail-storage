package com.mailstorage.core.feature.secondary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.feature.secondary.accumulator.EntityAccumulator;
import com.mailstorage.core.feature.secondary.accumulator.HBaseEntityAccumulator;
import com.mailstorage.core.feature.secondary.extractors.AvgEmailLengthFeatureExtractor;
import com.mailstorage.core.feature.secondary.extractors.TechCompanyRelevanceFeatureExtractor;
import com.mailstorage.data.mail.dao.RawHBaseDao;
import com.mailstorage.data.mail.entities.feature.secondary.AvgEmailLengthFeature;
import com.mailstorage.data.mail.entities.feature.secondary.SecondaryFeature;
import com.mailstorage.data.mail.entities.feature.secondary.TechCompanyRelevanceFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.List;

/**
 * @author metal
 */
@Configuration
public class SecondaryFeaturesConfiguration {
    @Bean
    public EntityAccumulator entityAccumulator(RawHBaseDao rawHBaseDao) {
        return new HBaseEntityAccumulator(rawHBaseDao);
    }

    @Bean
    public SecondaryFeaturesCalculationService secondaryFeaturesCalculationService(
            @Value("${mail.storage.secondary.features.calculation.rate}")
            long rate,
            EntityAccumulator entityAccumulator,
            CommonSecondaryFeatureManager<SecondaryFeatureManager> commonSecondaryFeatureManager)
    {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        return new SecondaryFeaturesCalculationService(scheduler,
                entityAccumulator, commonSecondaryFeatureManager, rate);
    }

    @Bean
    public SecondaryFeatureManager<AvgEmailLengthFeature> avgEmailLengthFeatureManager(
            AbstractHBDAO<Long, AvgEmailLengthFeature> dao)
    {
        return new SecondaryFeatureManager<>(new AvgEmailLengthFeatureExtractor(), dao);
    }

    @Bean
    public SecondaryFeatureManager<TechCompanyRelevanceFeature> techCompanyRelevanceFeatureManager(
            AbstractHBDAO<Long, TechCompanyRelevanceFeature> dao)
    {
        return new SecondaryFeatureManager<>(new TechCompanyRelevanceFeatureExtractor(), dao);
    }

    @Bean
    public CommonSecondaryFeatureManager<SecondaryFeatureManager> commonSecondaryFeatureManager(
            List<SecondaryFeatureManager> managers, AbstractHBDAO<Long, SecondaryFeature> secondaryFeatureDao)
    {
        return new CommonSecondaryFeatureManager<>(managers, secondaryFeatureDao);
    }
}
