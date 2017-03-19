package com.mailstorage.core.feature.primary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.feature.primary.extractors.CompositeFeatureExtractor;
import com.mailstorage.core.feature.primary.extractors.LengthFeatureExtractor;
import com.mailstorage.core.feature.primary.extractors.OrclRelevanceFeatureExtractor;
import com.mailstorage.core.feature.primary.extractors.SberRelevanceFeatureExtractor;
import com.mailstorage.core.primary.CommonPrimaryEntityManager;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.data.mail.entities.feature.primary.CompositeFeature;
import com.mailstorage.data.mail.entities.feature.primary.LengthFeature;
import com.mailstorage.data.mail.entities.feature.primary.OrclRelevanceFeature;
import com.mailstorage.data.mail.entities.feature.primary.SberRelevanceFeature;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author metal
 */
@Configuration
public class FeatureExtractorConfiguration {
    @Bean
    public BaseFeatureManager<OrclRelevanceFeature> orclRelevanceFeatureManager(
            AbstractHBDAO<Long, OrclRelevanceFeature> dao)
    {
        return new BaseFeatureManager<>(new OrclRelevanceFeatureExtractor(), dao);
    }

    @Bean
    public BaseFeatureManager<SberRelevanceFeature> sberRelevanceFeatureManager(
            AbstractHBDAO<Long, SberRelevanceFeature> dao)
    {
        return new BaseFeatureManager<>(new SberRelevanceFeatureExtractor(), dao);
    }

    @Bean
    public BaseFeatureManager<LengthFeature> lengthFeatureManager(
            AbstractHBDAO<Long, LengthFeature> dao)
    {
        return new BaseFeatureManager<>(new LengthFeatureExtractor(), dao);
    }

    @Bean
    public BaseFeatureManager<CompositeFeature> compositeFeatureManager(AbstractHBDAO<Long, CompositeFeature> dao) {
        return new BaseFeatureManager<>(new CompositeFeatureExtractor(), dao);
    }

    @Bean
    public CommonPrimaryEntityManager<PrimaryEntitiesRegistry, BaseFeatureManager> commonFeatureManager(
            List<BaseFeatureManager> managers)
    {
        return new CommonPrimaryEntityManager<>(managers);
    }
}
