package com.mailstorage.core.feature.secondary;

import com.mailstorage.core.feature.secondary.accumulator.EntityAccumulator;
import com.mailstorage.core.feature.secondary.accumulator.UserAccumulatedData;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

/**
 * @author metal
 *
 * Calculates and saves secondary features for some fixed time interval.
 */
public class SecondaryFeaturesCalculationTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SecondaryFeaturesCalculationTask.class);

    private static final long OFFSET = 1000;

    private final EntityAccumulator entityAccumulator;
    private final long rate;

    /**
     * Instantiates task.
     * @param entityAccumulator entity provider for specific time interval
     * @param rate task execution rate
     */
    public SecondaryFeaturesCalculationTask(EntityAccumulator entityAccumulator, long rate) {
        this.entityAccumulator = entityAccumulator;
        this.rate = rate;
    }

    @Override
    public void run() {
        logger.info("Calculating secondary features, current users: {}",
                entityAccumulator.getAccumulatedForPeriod(
                        Instant.now().minus(Duration.millis(rate + OFFSET)).getMillis(),
                        Instant.now().getMillis()
                ).stream()
                        .map(UserAccumulatedData::getUserId)
                        .collect(Collectors.toList()));
    }
}
