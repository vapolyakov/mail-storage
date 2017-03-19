package com.mailstorage.core.feature.secondary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.CoreTestConfiguration;
import com.mailstorage.data.mail.entities.feature.secondary.AvgEmailLengthFeature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.mailstorage.core.CoreTestData.TIMESTAMP;
import static com.mailstorage.core.CoreTestData.USER_ACCUMULATED_DATA;

/**
 * @author metal
 */
@RunWith(SpringRunner.class)
@PropertySource("classpath:core.properties")
@ContextConfiguration(classes = {
        CoreTestConfiguration.class
})
public class SecondaryFeatureManagerTest {
    @Autowired
    private SecondaryFeatureManager<AvgEmailLengthFeature> avgEmailLengthFeatureManager;

    @Autowired
    private AbstractHBDAO<Long, AvgEmailLengthFeature> avgEmailLengthFeatureDao;

    @Test
    public void secondaryFeatureManagerTest() throws IOException {
        AvgEmailLengthFeature feature = avgEmailLengthFeatureManager.extractAndSave(TIMESTAMP, USER_ACCUMULATED_DATA);
        Assert.assertEquals(20.0, feature.getAvgEmailLength(), 0.0000001);

        AvgEmailLengthFeature featureFromDb = avgEmailLengthFeatureDao.get(TIMESTAMP);
        Assert.assertEquals(20.0, featureFromDb.getAvgEmailLength(), 0.0000001);
    }

    @After
    public void clean() throws IOException {
        avgEmailLengthFeatureDao.delete(TIMESTAMP);
    }
}
