package com.mailstorage.data.mail.entities.feature.primary;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;

/**
 * @author metal
 */
@HBTable("mail")
public class LengthFeature extends BasePrimaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "feature", column = "length")
    private Long length;

    public LengthFeature() {
    }

    public LengthFeature(Long timestamp, Long length) {
        this.timestamp = timestamp;
        this.length = length;
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
        return length != null;
    }

    public Long getLength() {
        return length;
    }
}
