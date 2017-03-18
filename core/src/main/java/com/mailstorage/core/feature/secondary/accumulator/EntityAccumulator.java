package com.mailstorage.core.feature.secondary.accumulator;

import com.mailstorage.data.mail.entities.BasePrimaryEntity;

import java.util.List;

/**
 * @author metal
 *
 * Allows to accumulate extracted primary entities while processing new data
 * and return accumulated data for specific period of time grouped by user and entity type.
 */
public interface EntityAccumulator {
    /**
     * Stores information about given entity in accumulator.
     * @param userId entity belongs to this user
     * @param entity extracted entity to store
     */
    void put(String userId, BasePrimaryEntity entity);

    /**
     * Returns accumulated data for specific period of time grouped by user and entity type.
     * @param start get accumulated data from
     * @param end get accumulated data to
     * @return accumulated data for specified period of time
     */
    List<UserAccumulatedData> getAccumulatedForPeriod(long start, long end);
}
