package com.mailstorage.core.primary;

import com.mailstorage.data.mail.entities.BasePrimaryEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author metal
 *
 * Registry for extracted entities. Saves info in memory, allows to get specific data by entity class.
 */
@SuppressWarnings("unchecked")
public class PrimaryEntitiesRegistry {
    private final Map<Class, BasePrimaryEntity> entities = new HashMap<>();

    /**
     * Adds a new entity to this registry.
     * @param entity entity to add
     */
    public void registerPrimaryEntity(BasePrimaryEntity entity) {
        if (entity != null) {
            entities.put(entity.getClass(), entity);
        }
    }

    /**
     * Gets specific extracted entity by it's class.
     * @param clazz entity class to get
     * @return extracted entity and null if none was found
     */
    public <T> T get(Class<T> clazz) {
        return (T) entities.get(clazz);
    }

    /**
     * Adds data of specified registry to this instance and return itself.
     * @param another registry to add data from
     * @return itself
     */
    public PrimaryEntitiesRegistry merge(PrimaryEntitiesRegistry another) {
        another.getEntities().values().stream().forEach(this::registerPrimaryEntity);
        return this;
    }

    Map<Class, BasePrimaryEntity> getEntities() {
        return entities;
    }
}
