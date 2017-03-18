package com.mailstorage.data.mail.entities.artifact;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;

/**
 * @author metal
 */
@HBTable("mail")
public class AttachmentCountArtifact extends BasePrimaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "artifact", column = "att_count")
    private Integer count;

    public AttachmentCountArtifact() {
    }

    public AttachmentCountArtifact(Long timestamp, Integer count) {
        this.timestamp = timestamp;
        this.count = count;
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
        return count != null;
    }

    public Integer getCount() {
        return count;
    }
}
