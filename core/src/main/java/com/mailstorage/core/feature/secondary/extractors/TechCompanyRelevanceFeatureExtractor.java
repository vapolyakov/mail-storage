package com.mailstorage.core.feature.secondary.extractors;

import com.mailstorage.core.feature.secondary.accumulator.UserAccumulatedData;
import com.mailstorage.data.mail.entities.feature.primary.OrclRelevanceFeature;
import com.mailstorage.data.mail.entities.feature.secondary.TechCompanyRelevanceFeature;

import java.util.List;

/**
 * @author metal
 */
public class TechCompanyRelevanceFeatureExtractor implements SecondaryFeatureExtractor<TechCompanyRelevanceFeature> {
    @Override
    public TechCompanyRelevanceFeature extract(Long intervalId, UserAccumulatedData input) {
        List<OrclRelevanceFeature> availableTechRelevances = input.getSpecificEntities(OrclRelevanceFeature.class);
        return availableTechRelevances.isEmpty()
                ? null
                : new TechCompanyRelevanceFeature(intervalId,
                availableTechRelevances.stream()
                        .mapToDouble(OrclRelevanceFeature::getRelevance).average().getAsDouble());
    }
}
