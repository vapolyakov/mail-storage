package com.mailstorage.data.mail.dao;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.flipkart.hbaseobjectmapper.codec.DeserializationException;
import com.mailstorage.data.DataStorageTestConfiguration;
import com.mailstorage.data.DataStorageTestData;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.feature.primary.OrclRelevanceFeature;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

/**
 * @author metal
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DataStorageTestConfiguration.class)
public class RawHBaseDaoTest {
    @Autowired
    private RawHBaseDao rawHBaseDao;

    @Autowired
    private RawHBaseDaoHelper rawHBaseDaoHelper;

    @Autowired
    private AbstractHBDAO<Long, Mail> mailDao;

    @Autowired
    private AbstractHBDAO<Long, OrclWordArtifact> artifactDao;

    @Autowired
    private AbstractHBDAO<Long, OrclRelevanceFeature> featureDao;

    private long rowId;

    @Before
    public void initialize() throws IOException {
        rowId = System.currentTimeMillis();
        mailDao.persist(DataStorageTestData.createMail(rowId));
        artifactDao.persist(DataStorageTestData.createArtifact(rowId));
        featureDao.persist(DataStorageTestData.createFeature(rowId));
    }

    @Test
    public void testScan() throws IOException {
        Map<String, List<String>> familyMap = new HashMap<>();
        familyMap.put("artifact", Collections.singletonList("orcl_pos"));
        familyMap.put("feature", Collections.singletonList("orcl_rel"));

        Map<String, List<Object>> entitiesMap = rawHBaseDao.scan("mail", familyMap,
                Instant.now().minus(Duration.standardMinutes(5)).getMillis(),
                Arrays.asList(OrclWordArtifact.class, OrclRelevanceFeature.class));

        Assert.assertTrue(entitiesMap.containsKey(DataStorageTestData.USER_ID));

        List<Object> entitiesList = entitiesMap.get(DataStorageTestData.USER_ID);
        Assert.assertEquals(2, entitiesList.size());
        Assert.assertEquals(DataStorageTestData.POS, ((OrclWordArtifact) entitiesList.get(0)).getPos());
        Assert.assertEquals(DataStorageTestData.RELEVANCE, ((OrclRelevanceFeature) entitiesList.get(1)).getRelevance());
    }

    @Test
    public void testScanAndProcess() throws IOException {
        Map<String, List<String>> familyMap = new HashMap<>();
        familyMap.put("artifact", Collections.singletonList("orcl_pos"));
        familyMap.put("feature", Collections.singletonList("orcl_rel"));

        rawHBaseDao.scanAndProcess("mail", familyMap,
                Instant.now().minus(Duration.standardMinutes(5)).getMillis(),
                Instant.now().getMillis(),
                result -> {
                    try {
                        OrclWordArtifact artifact = (OrclWordArtifact) rawHBaseDaoHelper
                                .read(result, OrclWordArtifact.class);
                        Assert.assertEquals(DataStorageTestData.POS, artifact.getPos());
                    } catch (DeserializationException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @After
    public void clean() throws IOException {
        mailDao.delete(rowId);
    }
}
