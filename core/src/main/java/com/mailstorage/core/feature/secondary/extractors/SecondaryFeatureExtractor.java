package com.mailstorage.core.feature.secondary.extractors;

import com.mailstorage.core.feature.secondary.accumulator.UserAccumulatedData;
import com.mailstorage.data.mail.entities.feature.secondary.BaseSecondaryEntity;

/**
 * @author metal
 *
 * Allows to extract secondary feature from user accumulated input data.
 * @param <O> output entity type
 */
public interface SecondaryFeatureExtractor<O extends BaseSecondaryEntity> {
    /**
     * Extract BaseSecondaryEntity from input data and set proper id for it.
     * @param intervalId id of specific interval of time, user and data type, that this feature is calculated for
     * @param input input data
     * @return extracted entity
     */
    O extract(Long intervalId, UserAccumulatedData input);
}
