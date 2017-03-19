package com.mailstorage.core.feature.primary.extractors;

import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.core.primary.PrimaryEntityExtractor;
import com.mailstorage.data.mail.entities.feature.primary.SberRelevanceFeature;

import java.util.Random;

/**
 * @author metal
 */
public class SberRelevanceFeatureExtractor
        implements PrimaryEntityExtractor<PrimaryEntitiesRegistry, SberRelevanceFeature>
{
    @Override
    public SberRelevanceFeature extract(PrimaryEntitiesRegistry input) {
        SberRelevanceFeature artifact = input.get(SberRelevanceFeature.class);
        return artifact == null
                ? null
                : new SberRelevanceFeature(artifact.getTimestamp(), new Random().nextDouble());
    }
}
