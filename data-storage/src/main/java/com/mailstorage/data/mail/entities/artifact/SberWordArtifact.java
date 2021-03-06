package com.mailstorage.data.mail.entities.artifact;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;

/**
 * @author metal
 */
@HBTable("mail")
public class SberWordArtifact extends BasePrimaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "artifact", column = "sber_pos")
    private Integer pos;

    public SberWordArtifact() {
    }

    public SberWordArtifact(Long timestamp, Integer pos) {
        this.timestamp = timestamp;
        this.pos = pos;
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
        return pos != null;
    }

    public Integer getPos() {
        return pos;
    }
}
