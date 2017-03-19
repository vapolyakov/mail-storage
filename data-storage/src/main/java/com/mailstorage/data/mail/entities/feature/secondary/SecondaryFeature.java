package com.mailstorage.data.mail.entities.feature.secondary;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;

/**
 * @author metal
 */
@HBTable("secondary")
public class SecondaryFeature extends BaseSecondaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "general", column = "user_id")
    private String userId;

    @HBColumn(family = "general", column = "data_type")
    private String dataType;

    @HBColumn(family = "general", column = "from")
    private Long from;

    @HBColumn(family = "general", column = "to")
    private Long to;

    public SecondaryFeature() {
    }

    public SecondaryFeature(Long timestamp, String userId, String dataType, Long from, Long to) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.dataType = dataType;
        this.from = from;
        this.to = to;
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

    public String getUserId() {
        return userId;
    }

    public String getDataType() {
        return dataType;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }
}
