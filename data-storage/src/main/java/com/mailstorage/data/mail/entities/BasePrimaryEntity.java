package com.mailstorage.data.mail.entities;

import com.flipkart.hbaseobjectmapper.HBRecord;

/**
 * @author metal
 *
 * Entity that is connected to a single email identified by upload timestamp.
 */
public abstract class BasePrimaryEntity implements HBRecord<Long> {
    public abstract Long getTimestamp();
    public abstract void setTimestamp(Long timestamp);
}
