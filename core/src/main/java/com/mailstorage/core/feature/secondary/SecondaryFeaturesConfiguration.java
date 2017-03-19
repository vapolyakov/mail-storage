package com.mailstorage.core.feature.secondary;

import com.mailstorage.core.feature.secondary.accumulator.EntityAccumulator;
import com.mailstorage.core.feature.secondary.accumulator.HBaseEntityAccumulator;
import com.mailstorage.data.mail.dao.RawHBaseDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author metal
 */
@Configuration
public class SecondaryFeaturesConfiguration {
    @Bean
    public EntityAccumulator entityAccumulator(RawHBaseDao rawHBaseDao) {
        return new HBaseEntityAccumulator(rawHBaseDao);
    }

    @Bean
    public SecondaryFeaturesCalculationService secondaryFeaturesCalculationService4(
            @Value("${mail.storage.secondary.features.calculation.rate}")
            long rate,
            EntityAccumulator entityAccumulator)
    {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        return new SecondaryFeaturesCalculationService(scheduler, entityAccumulator, rate);
    }
}
