package com.mailstorage.core.feature.secondary.accumulator;

import com.mailstorage.data.mail.entities.BasePrimaryEntity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author metal
 *
 * Stores accumulated entities for specific user.
 */
public class UserAccumulatedData {
    private final String userId;
    private final long from;
    private final long to;
    private final Map<Class, List<BasePrimaryEntity>> mappedByClassEntities = new HashMap<>();

    /**
     * Creates new instance of UserAccumulatedData to start gathering data about specified user.
     * @param userId user to accumulate data about
     * @param from accumulated data from this point in time
     * @param to accumulated data to that point in time
     */
    public UserAccumulatedData(String userId, long from, long to) {
        this.userId = userId;
        this.from = from;
        this.to = to;
    }

    /**
     * Stores information about given entity.
     * @param entity extracted entity to store
     */
    public void add(BasePrimaryEntity entity) {
        Class<? extends BasePrimaryEntity> aClass = entity.getClass();
        if (!mappedByClassEntities.containsKey(aClass)) {
            mappedByClassEntities.put(aClass, new ArrayList<>());
        }
        mappedByClassEntities.get(aClass).add(entity);
    }

    public String getUserId() {
        return userId;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    /**
     * Returns all available data about given user.
     */
    public Map<Class, List<BasePrimaryEntity>> getMappedByClassEntities() {
        return mappedByClassEntities;
    }

    /**
     * Returns all available entities of specific class.
     */
    public <T extends BasePrimaryEntity> List<T> getSpecificEntities(Class<T> aClass) {
        if (mappedByClassEntities.containsKey(aClass)) {
            return mappedByClassEntities.get(aClass)
                    .stream()
                    .map(entity -> (T) entity)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
