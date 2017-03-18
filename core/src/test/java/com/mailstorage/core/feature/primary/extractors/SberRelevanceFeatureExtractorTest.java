package com.mailstorage.core.feature.primary.extractors;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.data.mail.entities.feature.SberRelevanceFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author metal
 */
public class SberRelevanceFeatureExtractorTest {
    private SberRelevanceFeatureExtractor extractor;

    @Before
    public void initialize() {
        extractor = new SberRelevanceFeatureExtractor();
    }

    @Test
    public void sberRelevanceFeatureExtractionTest() {
        PrimaryEntitiesRegistry registry = CoreTestData.getExtractedEntitiesRegistry();
        SberRelevanceFeature feature = extractor.extract(registry);

        Assert.assertNull(feature);
    }
}
