package com.mailstorage.core.feature.secondary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.CoreTestConfiguration;
import com.mailstorage.core.CoreTestData;
import com.mailstorage.data.mail.entities.feature.secondary.AvgEmailLengthFeature;
import com.mailstorage.data.mail.entities.feature.secondary.SecondaryFeature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author metal
 */
@RunWith(SpringRunner.class)
@PropertySource("classpath:core.properties")
@ContextConfiguration(classes = {
        CoreTestConfiguration.class
})
public class CommonSecondaryFeatureManagerTest {
    @Autowired
    private CommonSecondaryFeatureManager<SecondaryFeatureManager> commonSecondaryFeatureManager;

    @Autowired
    private AbstractHBDAO<Long, AvgEmailLengthFeature> avgEmailLengthFeatureDao;

    @Autowired
    private AbstractHBDAO<Long, SecondaryFeature> secondaryFeatureDao;

    @Test
    public void commonSecondaryFeatureManagerTest() throws IOException {
        long id = commonSecondaryFeatureManager.calculateFeatures(CoreTestData.USER_ACCUMULATED_DATA);

        AvgEmailLengthFeature feature = avgEmailLengthFeatureDao.get(id);
        Assert.assertEquals(20.0, feature.getAvgEmailLength(), 0.0000001);

        SecondaryFeature secondaryFeature = secondaryFeatureDao.get(id);
        Assert.assertEquals(CoreTestData.USER_ID, secondaryFeature.getUserId());
        Assert.assertEquals("mail", secondaryFeature.getDataType());
        Assert.assertEquals(1, secondaryFeature.getFrom().longValue());
        Assert.assertEquals(2, secondaryFeature.getTo().longValue());

        secondaryFeatureDao.delete(id);
    }
}
