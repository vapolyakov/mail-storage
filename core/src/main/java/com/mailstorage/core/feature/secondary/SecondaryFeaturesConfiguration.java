package com.mailstorage.core.feature.secondary;

import com.mailstorage.core.feature.secondary.accumulator.EntityAccumulator;
import com.mailstorage.core.feature.secondary.accumulator.HBaseEntityAccumulator;
import com.mailstorage.data.mail.dao.RawHBaseDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author metal
 */
@Configuration
public class SecondaryFeaturesConfiguration {
    @Bean
    public EntityAccumulator entityAccumulator(RawHBaseDao rawHBaseDao) {
        return new HBaseEntityAccumulator(rawHBaseDao);
    }
}
