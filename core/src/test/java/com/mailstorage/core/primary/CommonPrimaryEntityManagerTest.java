package com.mailstorage.core.primary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.CoreTestConfiguration;
import com.mailstorage.core.CoreTestData;
import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;
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
public class CommonPrimaryEntityManagerTest {
    @Autowired
    private CommonPrimaryEntityManager<Mail, BaseArtifactManager> commonArtifactManager;

    @Autowired
    private AbstractHBDAO<Long, OrclWordArtifact> orclWordArtifactDao;

    @Autowired
    private AbstractHBDAO<Long, SubjectArtifact> subjectArtifactDao;

    private static final Mail mail = CoreTestData.getMail();

    @Test
    public void calculateEntitiesTest() throws IOException {
        PrimaryEntitiesRegistry registry = commonArtifactManager.calculateEntities(mail);

        OrclWordArtifact orclFromRegistry = registry.get(OrclWordArtifact.class);
        Assert.assertEquals(18, orclFromRegistry.getPos().intValue());

        OrclWordArtifact orclFromDb = orclWordArtifactDao.get(mail.getTimestamp());
        Assert.assertEquals(18, orclFromDb.getPos().intValue());

        SubjectArtifact subjectFromRegistry = registry.get(SubjectArtifact.class);
        Assert.assertTrue(subjectFromRegistry.isFraudTo());

        SubjectArtifact subjectFromDb = subjectArtifactDao.get(mail.getTimestamp());
        Assert.assertTrue(subjectFromDb.isFraudTo());
    }

    @After
    public void clean() throws IOException {
        orclWordArtifactDao.delete(mail.getTimestamp());
    }
}
