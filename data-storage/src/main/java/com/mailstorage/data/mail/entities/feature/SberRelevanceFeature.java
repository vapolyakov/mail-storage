package com.mailstorage.data.mail.entities.feature;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;

/**
 * @author metal
 */
@HBTable("mail")
public class SberRelevanceFeature extends BasePrimaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "feature", column = "sber_rel")
    private Double relevance;

    public SberRelevanceFeature() {
    }

    public SberRelevanceFeature(Long timestamp, Double relevance) {
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

    public Double getRelevance() {
        return relevance;
    }
}
