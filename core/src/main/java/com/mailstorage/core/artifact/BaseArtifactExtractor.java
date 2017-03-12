package com.mailstorage.core.artifact;

import com.mailstorage.data.mail.entities.BasePrimaryEntity;
import com.mailstorage.data.mail.entities.Mail;

/**
 * @author metal
 */
public interface BaseArtifactExtractor<T extends BasePrimaryEntity> {
    T extract(Mail mail);
}
