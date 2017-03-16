package com.mailstorage.web;

import com.mailstorage.core.CoreConfiguration;
import com.mailstorage.data.mail.dao.DataStorageHBaseDaoConfiguration;
import com.mailstorage.data.raw.HdfsConfiguration;
import com.mailstorage.utils.file.IncomingFileSaver;
import com.mailstorage.utils.file.LocalFileManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author metal
 */
@Configuration
@ComponentScan("com.behavox.web.handlers")
@PropertySource("classpath:web-service.properties")
@Import({
        HdfsConfiguration.class,
        DataStorageHBaseDaoConfiguration.class,
        CoreConfiguration.class
})
public class MailStorageConfiguration {

    @Bean
    public LocalFileManager localFileManager(
            @Value("${mail.storage.local.file.manager.root.directory}")
            String rootDirectory)
    {
        return new LocalFileManager(rootDirectory);
    }

    @Bean
    public IncomingFileSaver incomingFileSaver(LocalFileManager localFileManager) {
        return new IncomingFileSaver(localFileManager);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
