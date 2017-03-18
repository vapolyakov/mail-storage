package com.mailstorage.core.feature.primary.extractors;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.data.mail.entities.feature.OrclRelevanceFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author metal
 */
public class OrclRelevanceFeatureExtractorTest {
    private OrclRelevanceFeatureExtractor extractor;

    @Before
    public void initialize() {
        extractor = new OrclRelevanceFeatureExtractor();
    }

    @Test
    public void orclRelevanceFeatureExtractionTest() {
        PrimaryEntitiesRegistry registry = CoreTestData.getExtractedEntitiesRegistry();
        OrclRelevanceFeature feature = extractor.extract(registry);

        Assert.assertTrue(1.0 > feature.getRelevance());
        Assert.assertTrue(0.0 <= feature.getRelevance());
    }
}
