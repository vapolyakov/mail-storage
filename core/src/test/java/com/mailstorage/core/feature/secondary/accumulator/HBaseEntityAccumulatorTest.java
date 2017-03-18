package com.mailstorage.core.feature.secondary.accumulator;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.CoreTestConfiguration;
import com.mailstorage.core.CoreTestData;
import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.core.feature.primary.BaseFeatureManager;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.data.mail.dao.RawHBaseDao;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.feature.OrclRelevanceFeature;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author metal
 */
@RunWith(SpringRunner.class)
@PropertySource("classpath:core.properties")
@ContextConfiguration(classes = {
        CoreTestConfiguration.class
})
public class HBaseEntityAccumulatorTest {
    @Autowired
    private BaseArtifactManager<OrclWordArtifact> orclWordArtifactManager;

    @Autowired
    private AbstractHBDAO<Long, Mail> mailDao;

    @Autowired
    private BaseFeatureManager<OrclRelevanceFeature> orclRelevanceFeatureManager;

    @Autowired
    private RawHBaseDao rawHBaseDao;

    private static final Mail mail = CoreTestData.getMail();

    @Test
    public void hbaseEntityAccumulatorTest() throws IOException {
        mailDao.persist(mail);

        OrclWordArtifact artifact = orclWordArtifactManager.extractAndSave(mail);

        PrimaryEntitiesRegistry registry = new PrimaryEntitiesRegistry();
        registry.registerPrimaryEntity(mail);
        registry.registerPrimaryEntity(artifact);

        OrclRelevanceFeature feature = orclRelevanceFeatureManager.extractAndSave(registry);

        HBaseEntityAccumulator accumulator = new HBaseEntityAccumulator(rawHBaseDao);

        List<UserAccumulatedData> userAccumulatedDatas =
                accumulator.getAccumulatedForPeriod(
                        Instant.now().minus(Duration.standardMinutes(5)).getMillis(),
                        Instant.now().getMillis());

        Assert.assertEquals(1, userAccumulatedDatas.size());

        UserAccumulatedData userAccumulatedData = userAccumulatedDatas.get(0);
        Assert.assertEquals(CoreTestData.USER_ID, userAccumulatedData.getUserId());

        Assert.assertEquals(3, userAccumulatedData.getMappedByClassEntities().size());
        Assert.assertTrue(userAccumulatedData.getMappedByClassEntities().containsKey(OrclRelevanceFeature.class));

        Assert.assertEquals(feature.getRelevance(), userAccumulatedData
                .getSpecificEntities(OrclRelevanceFeature.class).get(0).getRelevance());
    }

    @After
    public void clean() throws IOException {
        mailDao.delete(mail.getTimestamp());
    }
}
