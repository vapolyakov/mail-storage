package com.mailstorage.core.feature.secondary;

import com.mailstorage.core.feature.secondary.accumulator.EntityAccumulator;
import org.springframework.scheduling.TaskScheduler;

import javax.annotation.PostConstruct;

/**
 * @author metal
 *
 * Starts regularly secondary features calculation and saving.
 */
public class SecondaryFeaturesCalculationService {
    private final TaskScheduler scheduler;
    private final EntityAccumulator entityAccumulator;
    private final CommonSecondaryFeatureManager<SecondaryFeatureManager> commonSecondaryFeatureManager;
    private final long rate;

    public SecondaryFeaturesCalculationService(
            TaskScheduler scheduler,
            EntityAccumulator entityAccumulator,
            CommonSecondaryFeatureManager<SecondaryFeatureManager> commonSecondaryFeatureManager,
            long rate)
    {
        this.scheduler = scheduler;
        this.entityAccumulator = entityAccumulator;
        this.commonSecondaryFeatureManager = commonSecondaryFeatureManager;
        this.rate = rate;
    }

    @PostConstruct
    public void start() {
        scheduler.scheduleAtFixedRate(
                new SecondaryFeaturesCalculationTask(commonSecondaryFeatureManager, entityAccumulator, rate), rate);
    }
}
