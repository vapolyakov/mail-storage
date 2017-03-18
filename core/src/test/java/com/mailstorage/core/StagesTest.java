package com.mailstorage.core;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;
import com.mailstorage.data.mail.entities.feature.LengthFeature;
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
public class StagesTest {
    @Autowired
    private AbstractHBDAO<Long, SubjectArtifact> subjectArtifactDao;

    @Autowired
    private AbstractHBDAO<Long, LengthFeature> lengthFeatureDao;

    @Autowired
    private Stages stages;

    private static final Mail mail = CoreTestData.getMail();

    @Test
    public void processingChainFromArtifactsTest() throws InterruptedException, IOException {
        stages.setRemoveLocalFiles(false);
        stages.extractArtifacts(mail, true);

        Thread.sleep(2000);

        SubjectArtifact subjectArtifact = subjectArtifactDao.get(mail.getTimestamp());
        Assert.assertNotNull(subjectArtifact);
        Assert.assertTrue(subjectArtifact.isFraudTo());
        Assert.assertNull(subjectArtifact.getSuspiciousWords());

        LengthFeature lengthFeature = lengthFeatureDao.get(mail.getTimestamp());
        Assert.assertEquals(975L, lengthFeature.getLength().longValue());

        Assert.assertTrue(mail.getEmailLocalFile().exists());
    }

    @After
    public void clean() throws IOException {
        subjectArtifactDao.delete(mail.getTimestamp());
    }
}
