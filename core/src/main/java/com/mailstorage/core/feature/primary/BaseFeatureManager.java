package com.mailstorage.core.feature.primary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.core.primary.PrimaryEntityExtractor;
import com.mailstorage.core.primary.PrimaryEntityManager;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;
import com.mailstorage.data.mail.entities.EntityType;

/**
 * @author metal
 */
public class BaseFeatureManager<T extends BasePrimaryEntity> extends PrimaryEntityManager<PrimaryEntitiesRegistry, T> {
    public BaseFeatureManager(PrimaryEntityExtractor<PrimaryEntitiesRegistry, T> extractor, AbstractHBDAO<Long, T> dao) {
        super(extractor, dao);
    }

    @Override
    protected EntityType getEntityType() {
        return EntityType.FEATURE;
    }
}
