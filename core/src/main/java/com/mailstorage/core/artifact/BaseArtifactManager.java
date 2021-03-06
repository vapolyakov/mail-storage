package com.mailstorage.core.artifact;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.primary.PrimaryEntityExtractor;
import com.mailstorage.core.primary.PrimaryEntityManager;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;
import com.mailstorage.data.mail.entities.EntityType;
import com.mailstorage.data.mail.entities.Mail;

/**
 * @author metal
 */
public class BaseArtifactManager<T extends BasePrimaryEntity> extends PrimaryEntityManager<Mail, T> {
    public BaseArtifactManager(PrimaryEntityExtractor<Mail, T> extractor, AbstractHBDAO<Long, T> dao) {
        super(extractor, dao);
    }

    @Override
    protected EntityType getEntityType() {
        return EntityType.ARTIFACT;
    }
}
