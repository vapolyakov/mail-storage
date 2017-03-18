package com.mailstorage.data;

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
        DataStorageHBaseDaoConfiguration.class
})
public class DataStorageTestConfiguration {
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
