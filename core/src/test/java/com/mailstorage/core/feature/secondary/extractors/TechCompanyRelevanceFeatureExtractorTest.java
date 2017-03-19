package com.mailstorage.core.feature.secondary.extractors;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.core.feature.secondary.accumulator.UserAccumulatedData;
import com.mailstorage.data.mail.entities.feature.primary.OrclRelevanceFeature;
import com.mailstorage.data.mail.entities.feature.secondary.TechCompanyRelevanceFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author metal
 */
public class TechCompanyRelevanceFeatureExtractorTest {
    private TechCompanyRelevanceFeatureExtractor extractor;

    @Before
    public void initialize() {
        extractor = new TechCompanyRelevanceFeatureExtractor();
    }

    @Test
    public void compositeFeatureExtractionTest() {
        long id = 123;
        UserAccumulatedData data = new UserAccumulatedData(CoreTestData.USER_ID, 1, 2);
        data.add(new OrclRelevanceFeature(id, 0.2));
        data.add(new OrclRelevanceFeature(id, 0.4));
        data.add(new OrclRelevanceFeature(id, 0.6));
        TechCompanyRelevanceFeature feature = extractor.extract(id, data);

        Assert.assertNotNull(feature);
        Assert.assertNotNull(feature.getRelevance());
        Assert.assertEquals(0.4, feature.getRelevance(), 0.000001);
    }
}
