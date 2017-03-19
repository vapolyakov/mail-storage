package com.mailstorage.core.feature.secondary.accumulator;

import com.mailstorage.data.mail.entities.BasePrimaryEntity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author metal
 *
 * Accumulator that keeps extracted entities in memory and removes them ones its were read.
 */
public class InMemoryEntityAccumulator implements EntityAccumulator {
    private final Map<String, NavigableMap<Long, List<BasePrimaryEntity>>> data = new HashMap<>();

    private final Object lock = new Object();

    @Override
    public void put(String userId, BasePrimaryEntity entity) {
        synchronized (lock) {
            if (!data.containsKey(userId)) {
                data.put(userId, new TreeMap<>());
            }
            if (!data.get(userId).containsKey(entity.getTimestamp())) {
                data.get(userId).put(entity.getTimestamp(), new ArrayList<>());
            }
            data.get(userId).get(entity.getTimestamp()).add(entity);
        }
    }

    @Override
    public List<UserAccumulatedData> getAccumulatedForPeriod(long start, long end) {
        return data.entrySet().stream()
                .map(userIdToEntities -> {
                    String userId = userIdToEntities.getKey();
                    SortedMap<Long, List<BasePrimaryEntity>> entitiesSubset =
                            userIdToEntities.getValue().subMap(start, end);

                    UserAccumulatedData userAccumulatedData = new UserAccumulatedData(userId, start, end);
                    entitiesSubset.values().stream()
                            .forEach(entities -> entities.forEach(userAccumulatedData::add));

                    removeReadData(userId, entitiesSubset.keySet());

                    return userAccumulatedData;
                })
                .collect(Collectors.toList());
    }

    int currentSize() {
        return data.entrySet().stream()
                .map(Map.Entry::getValue)
                .mapToInt(
                        entitiesMap -> entitiesMap.entrySet().stream()
                                .map(Map.Entry::getValue)
                                .mapToInt(Collection::size)
                                .sum())
                .sum();
    }

    private void removeReadData(String userId, Collection<Long> entityIds) {
        synchronized (lock) {
            entityIds.forEach(id -> data.get(userId).remove(id));
        }
    }
}
