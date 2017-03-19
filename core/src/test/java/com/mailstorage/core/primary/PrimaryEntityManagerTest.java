package com.mailstorage.core.primary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.CoreTestConfiguration;
import com.mailstorage.core.CoreTestData;
import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.core.feature.primary.BaseFeatureManager;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.feature.primary.OrclRelevanceFeature;
import org.junit.After;
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
public class PrimaryEntityManagerTest {
    @Autowired
    private BaseArtifactManager<OrclWordArtifact> orclWordArtifactManager;

    @Autowired
    private AbstractHBDAO<Long, OrclWordArtifact> orclWordArtifactDao;

    @Autowired
    private BaseFeatureManager<OrclRelevanceFeature> orclRelevanceFeatureManager;

    @Autowired
    private AbstractHBDAO<Long, OrclRelevanceFeature> orclRelevanceDao;

    private static final Mail mail = CoreTestData.getMail();

    @Test
    public void artifactManagerTest() throws IOException {
        OrclWordArtifact artifact = orclWordArtifactManager.extractAndSave(mail);
        Assert.assertEquals(18, artifact.getPos().intValue());

        OrclWordArtifact artifactFromDb = orclWordArtifactDao.get(mail.getTimestamp());
        Assert.assertEquals(18, artifactFromDb.getPos().intValue());
    }

    @Test
    public void featureManagerTest() throws IOException {
        PrimaryEntitiesRegistry registry = new PrimaryEntitiesRegistry();
        registry.registerPrimaryEntity(mail);
        registry.registerPrimaryEntity(orclWordArtifactManager.extractAndSave(mail));

        OrclRelevanceFeature feature = orclRelevanceFeatureManager.extractAndSave(registry);
        Assert.assertTrue(1.0 > feature.getRelevance());
        Assert.assertTrue(0.0 <= feature.getRelevance());

        OrclRelevanceFeature featureFromDb = orclRelevanceDao.get(mail.getTimestamp());
        Assert.assertTrue(1.0 > featureFromDb.getRelevance());
        Assert.assertTrue(0.0 <= featureFromDb.getRelevance());
    }

    @After
    public void clean() throws IOException {
        orclWordArtifactDao.delete(mail.getTimestamp());
    }
}
