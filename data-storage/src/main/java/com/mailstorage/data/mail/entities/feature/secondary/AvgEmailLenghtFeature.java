package com.mailstorage.data.mail.entities.feature.secondary;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;

/**
 * @author metal
 */
@HBTable("secondary")
public class AvgEmailLenghtFeature extends BaseSecondaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "feature", column = "avg")
    private Double avgEmailLength;

    public AvgEmailLenghtFeature() {
    }

    public AvgEmailLenghtFeature(Long timestamp, Double avgEmailLength) {
        this.timestamp = timestamp;
        this.avgEmailLength = avgEmailLength;
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

    public Double getAvgEmailLength() {
        return avgEmailLength;
    }
}
