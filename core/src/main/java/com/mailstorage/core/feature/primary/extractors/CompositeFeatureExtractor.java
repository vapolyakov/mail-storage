package com.mailstorage.core.feature.primary.extractors;

import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.core.primary.PrimaryEntityExtractor;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;
import com.mailstorage.data.mail.entities.feature.CompositeFeature;

import java.util.Random;

/**
 * @author metal
 */
public class CompositeFeatureExtractor implements PrimaryEntityExtractor<PrimaryEntitiesRegistry, CompositeFeature> {
    @Override
    public CompositeFeature extract(PrimaryEntitiesRegistry input) {
        SubjectArtifact subjectArtifact = input.get(SubjectArtifact.class);
        OrclWordArtifact orclWordArtifact = input.get(OrclWordArtifact.class);

        return checkForFraud(subjectArtifact, orclWordArtifact)
                ? new CompositeFeature(subjectArtifact.getTimestamp(), new Random().nextDouble())
                : null;
    }

    private boolean checkForFraud(SubjectArtifact subjectArtifact, OrclWordArtifact orclWordArtifact) {
        return subjectArtifact != null
                && orclWordArtifact != null
                && subjectArtifact.isFraudTo()
                && subjectArtifact.getSuspiciousWords() != null
                && !subjectArtifact.getSuspiciousWords().isEmpty();
    }
}
