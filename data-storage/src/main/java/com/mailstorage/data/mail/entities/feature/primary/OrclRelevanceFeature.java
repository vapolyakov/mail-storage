package com.mailstorage.data.mail.entities.feature.primary;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;

/**
 * @author metal
 */
@HBTable("mail")
public class OrclRelevanceFeature extends BasePrimaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "feature", column = "orcl_rel")
    private Double relevance;

    public OrclRelevanceFeature() {
    }

    public OrclRelevanceFeature(Long timestamp, Double relevance) {
        this.timestamp = timestamp;
        this.relevance = relevance;
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
        return relevance != null;
    }

    public Double getRelevance() {
        return relevance;
    }
}
