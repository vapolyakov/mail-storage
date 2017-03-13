package com.mailstorage.data.mail.entities.artifact;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;

/**
 * @author metal
 */
@HBTable("mail")
public class OrclWordArtifact extends BasePrimaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "artifact", column = "orcl_pos")
    private Integer pos;

    public OrclWordArtifact() {
    }

    public OrclWordArtifact(Long timestamp, Integer pos) {
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

    public Integer getPos() {
        return pos;
    }
}
