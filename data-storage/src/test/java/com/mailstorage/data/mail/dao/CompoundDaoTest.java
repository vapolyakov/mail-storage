package com.mailstorage.data.mail.dao;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author metal
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DataStorageHBaseDaoConfiguration.class)
public class CompoundDaoTest {
    private static final String MESSAGE = "my secret message with ORCL";
    private static final Integer POS = 399;

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
        expectedMail = createMail(rowId);
        expectedArtifact = createArtifact(rowId);
    }

    @Test
    public void testCRUD() throws IOException {
        mailDao.persist(expectedMail);
        artifactDao.persist(expectedArtifact);

        Assert.assertEquals(MESSAGE, mailDao.get(rowId).getMessage());
        Assert.assertEquals(POS, artifactDao.get(rowId).getPos());

        mailDao.delete(rowId);
        Assert.assertNull(mailDao.get(rowId));
    }

    private static Mail createMail(long row) {
        Map<String, String> attachments = new HashMap<>();
        attachments.put("attach1.txt", "application/json");

        return new Mail(row, "123456", "my_email.eml", "hdfs_id1",
                "from_metal", "Wed Mar 15 23:25:12 MSK 2017",
                Arrays.asList("gek", "pek"), new ArrayList<>(), new ArrayList<>(),
                "best subject", MESSAGE, attachments);
    }

    private static OrclWordArtifact createArtifact(long row) {
        return new OrclWordArtifact(row, POS);
    }
}
