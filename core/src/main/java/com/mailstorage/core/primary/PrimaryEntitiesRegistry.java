package com.mailstorage.core.primary;

import com.mailstorage.data.mail.entities.BasePrimaryEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author metal
 */
@SuppressWarnings("unchecked")
public class PrimaryEntitiesRegistry {
    private final Map<Class, BasePrimaryEntity> entities = new HashMap<>();

    public void registerPrimaryEntity(BasePrimaryEntity entity) {
        if (entity != null) {
            entities.put(entity.getClass(), entity);
        }
    }

    public <T> T get(Class<T> clazz) {
        return (T) entities.get(clazz);
    }

    public PrimaryEntitiesRegistry merge(PrimaryEntitiesRegistry another) {
        another.getEntities().values().stream().forEach(this::registerPrimaryEntity);
        return this;
    }

    Map<Class, BasePrimaryEntity> getEntities() {
        return entities;
    }
}
