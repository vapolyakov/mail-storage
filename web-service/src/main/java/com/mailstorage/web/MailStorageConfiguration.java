package com.mailstorage.web;

import com.mailstorage.data.raw.HdfsConfiguration;
import com.mailstorage.utils.file.IncomingFileSaver;
import com.mailstorage.utils.file.LocalFileManager;
import com.mailstorage.web.artifacts.LengthExtractor;
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
        HdfsConfiguration.class
})
public class MailStorageConfiguration {
    @Bean
    public LengthExtractor lengthExtractor(@Value("${mail.storage.max.file.length}") long maxLength) {
        return new LengthExtractor(maxLength);
    }

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
