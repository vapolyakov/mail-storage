package com.mailstorage.data.mail.dao;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.DataStorageTestConfiguration;
import com.mailstorage.data.DataStorageTestData;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.mailstorage.data.DataStorageTestData.MESSAGE;
import static com.mailstorage.data.DataStorageTestData.POS;

/**
 * @author metal
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DataStorageTestConfiguration.class)
public class CompoundDaoTest {
    @Autowired
    private AbstractHBDAO<Long, Mail> mailDao;

    @Autowired
    private AbstractHBDAO<Long, OrclWordArtifact> artifactDao;

    private long rowId;
    private Mail expectedMail;
    private OrclWordArtifact expectedArtifact;

    @Before
    public void initialize() {
        rowId = System.currentTimeMillis();
        expectedMail = DataStorageTestData.createMail(rowId);
        expectedArtifact = DataStorageTestData.createArtifact(rowId);
    }

    @Test
    public void testInsertReadAndDelete() throws IOException {
        mailDao.persist(expectedMail);
        artifactDao.persist(expectedArtifact);

        Assert.assertEquals(MESSAGE, mailDao.get(rowId).getMessage());
        Assert.assertEquals(POS, artifactDao.get(rowId).getPos());

        mailDao.delete(rowId);
        Assert.assertNull(mailDao.get(rowId));
    }

    @After
    public void clean() throws IOException {
        if (mailDao.get(rowId) != null) {
            mailDao.delete(rowId);
        }
    }
}
