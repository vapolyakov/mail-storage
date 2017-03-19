package com.mailstorage.core.feature.secondary.extractors;

import com.mailstorage.core.feature.secondary.accumulator.UserAccumulatedData;
import com.mailstorage.data.mail.entities.feature.primary.LengthFeature;
import com.mailstorage.data.mail.entities.feature.secondary.AvgEmailLengthFeature;

import java.util.List;

/**
 * @author metal
 */
public class AvgEmailLengthFeatureExtractor implements SecondaryFeatureExtractor<AvgEmailLengthFeature> {
    @Override
    public AvgEmailLengthFeature extract(Long intervalId, UserAccumulatedData input) {
        List<LengthFeature> availableLengths = input.getSpecificEntities(LengthFeature.class);
        return availableLengths.isEmpty()
                ? null
                : new AvgEmailLengthFeature(intervalId,
                availableLengths.stream().mapToLong(LengthFeature::getLength).average().getAsDouble());
    }
}
