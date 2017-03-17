package com.mailstorage.core.primary;

import com.mailstorage.data.mail.entities.BasePrimaryEntity;

/**
 * @author metal
 *
 * Base interface of primary entity extractor. Allows to extract primary entity from some input data.
 * @param <I> input data type
 * @param <O> output entity type
 */
public interface PrimaryEntityExtractor<I, O extends BasePrimaryEntity> {
    /**
     * Extract BasePrimaryEntity from input data.
     * @param input input data
     * @return extracted entity
     */
    O extract(I input);
}
