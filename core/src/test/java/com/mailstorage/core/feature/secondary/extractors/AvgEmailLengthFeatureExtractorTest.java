package com.mailstorage.core.feature.secondary.extractors;

import com.mailstorage.data.mail.entities.feature.secondary.AvgEmailLengthFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.mailstorage.core.CoreTestData.TIMESTAMP;
import static com.mailstorage.core.CoreTestData.USER_ACCUMULATED_DATA;

/**
 * @author metal
 */
public class AvgEmailLengthFeatureExtractorTest {
    private AvgEmailLengthFeatureExtractor extractor;

    @Before
    public void initialize() {
        extractor = new AvgEmailLengthFeatureExtractor();
    }

    @Test
    public void compositeFeatureExtractionTest() {
        AvgEmailLengthFeature feature = extractor.extract(TIMESTAMP, USER_ACCUMULATED_DATA);

        Assert.assertNotNull(feature);
        Assert.assertNotNull(feature.getAvgEmailLength());
        Assert.assertEquals(20.0, feature.getAvgEmailLength(), 0.000001);
    }
}
