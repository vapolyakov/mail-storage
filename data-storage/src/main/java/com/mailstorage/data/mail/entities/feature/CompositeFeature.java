package com.mailstorage.data.mail.entities.feature;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;

/**
 * @author metal
 *
 * Composite feature that calculates fraud probability based on ORCL word and subject artifacts.
 */
@HBTable("mail")
public class CompositeFeature extends BasePrimaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "feature", column = "fraud")
    private Double fraud;

    public CompositeFeature() {
    }

    public CompositeFeature(Long timestamp, Double fraud) {
        this.timestamp = timestamp;
        this.fraud = fraud;
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

    @Override
    public boolean isInitialized() {
        return fraud != null;
    }

    public Double getFraud() {
        return fraud;
    }
}
