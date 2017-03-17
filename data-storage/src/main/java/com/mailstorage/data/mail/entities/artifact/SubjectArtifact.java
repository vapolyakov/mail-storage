package com.mailstorage.data.mail.entities.artifact;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;

import java.util.List;

/**
 * @author metal
 *
 * Artifact that checks subject for suspicious words and at the same time if there is any fraud "to" email addresses.
 */
@HBTable("mail")
public class SubjectArtifact extends BasePrimaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "artifact", column = "subject_has")
    private List<String> suspiciousWords;

    @HBColumn(family = "artifact", column = "fraud_to")
    private Boolean fraudTo;

    public SubjectArtifact() {
    }

    public SubjectArtifact(Long timestamp, List<String> suspiciousWords, Boolean fraudTo) {
        this.timestamp = timestamp;
        this.suspiciousWords = suspiciousWords;
        this.fraudTo = fraudTo;
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public Long composeRowKey() {
        return getTimestamp();
    }

    @Override
    public void parseRowKey(Long rowKey) {
        setTimestamp(rowKey);
    }

    public List<String> getSuspiciousWords() {
        return suspiciousWords;
    }

    public Boolean isFraudTo() {
        return fraudTo;
    }
}
