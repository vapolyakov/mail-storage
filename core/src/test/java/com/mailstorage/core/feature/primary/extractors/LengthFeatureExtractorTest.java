package com.mailstorage.core.feature.primary.extractors;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.data.mail.entities.feature.primary.LengthFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author metal
 */
public class LengthFeatureExtractorTest {
    private LengthFeatureExtractor extractor;

    @Before
    public void initialize() {
        extractor = new LengthFeatureExtractor();
    }

    @Test
    public void lengthFeatureExtractionTest() {
        PrimaryEntitiesRegistry registry = CoreTestData.getExtractedEntitiesRegistry();
        LengthFeature feature = extractor.extract(registry);

        Assert.assertEquals(975L, feature.getLength().longValue());
    }
}
