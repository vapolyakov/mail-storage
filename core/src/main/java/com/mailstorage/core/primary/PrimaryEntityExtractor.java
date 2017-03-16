package com.mailstorage.core.primary;

import com.mailstorage.data.mail.entities.BasePrimaryEntity;

/**
 * @author metal
 */
public interface PrimaryEntityExtractor<I, O extends BasePrimaryEntity> {
    O extract(I input);
}
