package com.mailstorage.data.mail;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * @author metal
 */
@org.springframework.context.annotation.Configuration
public class DataStorageHBaseConfiguration {
    @Bean
    public Configuration hbaseConfiguration() {
        return HBaseConfiguration.create();
    }

    @Bean
    public DataStorageHBasePreparer dataStorageHBasePreparer(Configuration conf) throws IOException {
        return new DataStorageHBasePreparer(conf);
    }
}
