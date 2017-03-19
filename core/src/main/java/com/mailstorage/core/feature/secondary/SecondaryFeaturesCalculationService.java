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
    private final long rate;

    public SecondaryFeaturesCalculationService(TaskScheduler scheduler, EntityAccumulator entityAccumulator, long rate) {
        this.scheduler = scheduler;
        this.entityAccumulator = entityAccumulator;
        this.rate = rate;
    }

    @PostConstruct
    public void start() {
        scheduler.scheduleAtFixedRate(new SecondaryFeaturesCalculationTask(entityAccumulator, rate), rate);
    }
}
