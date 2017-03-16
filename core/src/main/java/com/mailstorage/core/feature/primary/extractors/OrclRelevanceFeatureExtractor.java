package com.mailstorage.core.feature.primary.extractors;

import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.core.primary.PrimaryEntityExtractor;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.feature.OrclRelevanceFeature;

import java.util.Random;

/**
 * @author metal
 */
public class OrclRelevanceFeatureExtractor
        implements PrimaryEntityExtractor<PrimaryEntitiesRegistry, OrclRelevanceFeature>
{
    @Override
    public OrclRelevanceFeature extract(PrimaryEntitiesRegistry input) {
        OrclWordArtifact artifact = input.get(OrclWordArtifact.class);
        return artifact == null
                ? null
                : new OrclRelevanceFeature(artifact.getTimestamp(), new Random().nextDouble());
    }
}
