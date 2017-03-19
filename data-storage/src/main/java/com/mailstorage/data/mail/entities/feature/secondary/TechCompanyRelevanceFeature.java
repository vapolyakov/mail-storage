package com.mailstorage.data.mail.entities.feature.secondary;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;

/**
 * @author metal
 */
@HBTable("secondary")
public class TechCompanyRelevanceFeature extends BaseSecondaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "feature", column = "tech_company_rel")
    private Double relevance;

    public TechCompanyRelevanceFeature() {
    }

    public TechCompanyRelevanceFeature(Long timestamp, Double relevance) {
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
