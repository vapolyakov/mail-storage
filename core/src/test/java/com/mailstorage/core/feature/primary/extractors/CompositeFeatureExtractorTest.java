package com.mailstorage.core.feature.primary.extractors;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.data.mail.entities.feature.primary.CompositeFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author metal
 */
public class CompositeFeatureExtractorTest {
    private CompositeFeatureExtractor extractor;

    @Before
    public void initialize() {
        extractor = new CompositeFeatureExtractor();
    }

    @Test
    public void compositeFeatureExtractionTest() {
        PrimaryEntitiesRegistry registry = CoreTestData.getExtractedEntitiesRegistry();
        CompositeFeature feature = extractor.extract(registry);

        Assert.assertNull(feature);
    }
}
