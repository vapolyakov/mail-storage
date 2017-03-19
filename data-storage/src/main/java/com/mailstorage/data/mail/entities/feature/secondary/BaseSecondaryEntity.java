package com.mailstorage.data.mail.entities.feature.secondary;

import com.flipkart.hbaseobjectmapper.HBRecord;

/**
 * @author metal
 *
 * Entity that is connected to an user and a specific data type.
 */
public abstract class BaseSecondaryEntity implements HBRecord<Long> {
    public abstract Long getTimestamp();
    public abstract void setTimestamp(Long timestamp);
}
