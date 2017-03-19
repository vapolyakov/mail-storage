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

    private final CommonSecondaryFeatureManager<SecondaryFeatureManager> commonSecondaryFeatureManager;
    private final EntityAccumulator entityAccumulator;
    private final long rate;

    /**
     * Instantiates task.
     * @param commonSecondaryFeatureManager manager that calculates and saves secondary features
     * @param entityAccumulator entity provider for specific time interval
     * @param rate task execution rate
     */
    public SecondaryFeaturesCalculationTask(
            CommonSecondaryFeatureManager<SecondaryFeatureManager> commonSecondaryFeatureManager,
            EntityAccumulator entityAccumulator, long rate)
    {
        this.commonSecondaryFeatureManager = commonSecondaryFeatureManager;
        this.entityAccumulator = entityAccumulator;
        this.rate = rate;
    }

    @Override
    public void run() {
        logger.info("Starting to calculate secondary features for all users");
        entityAccumulator
                .getAccumulatedForPeriod(getStartingTime(), getEndingTime())
                .forEach(commonSecondaryFeatureManager::calculateFeatures);
    }

    private long getEndingTime() {
        return Instant.now().getMillis();
    }

    private long getStartingTime() {
        return Instant.now().minus(Duration.millis(rate + OFFSET)).getMillis();
    }
}
