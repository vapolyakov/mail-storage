package com.mailstorage.data;

import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.feature.primary.OrclRelevanceFeature;

import java.util.*;

/**
 * @author metal
 */
public class DataStorageTestData {
    public static final String FILE_NAME = "received.eml";
    public static final String USER_ID = "metal_id";

    public static final String MESSAGE = "my secret message with ORCL";
    public static final Integer POS = 399;
    public static final Double RELEVANCE = 0.234;

    public static Mail createMail(long row) {
        Map<String, String> attachments = new HashMap<>();
        attachments.put("attach1.txt", "application/json");

        return new Mail(row, USER_ID, FILE_NAME, "hdfs_id1",
                "from_metal", "Wed Mar 15 23:25:12 MSK 2017",
                Arrays.asList("gek", "pek"), new ArrayList<>(), new ArrayList<>(),
                "best subject", MESSAGE, attachments);
    }

    public static OrclWordArtifact createArtifact(long row) {
        return new OrclWordArtifact(row, POS);
    }

    public static OrclRelevanceFeature createFeature(long row) {
        return new OrclRelevanceFeature(row, RELEVANCE);
    }
}
