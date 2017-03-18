package com.mailstorage.core;

import com.mailstorage.data.mail.dao.DataStorageHBaseDaoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author metal
 */
@Configuration
@Import({
        DataStorageHBaseDaoConfiguration.class,
        CoreConfiguration.class
})
public class CoreTestConfiguration {
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
