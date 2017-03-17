package com.mailstorage.data.mail;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author metal
 *
 * Functionally configures HBase on application start. Creates needed tables, etc.
 */
public class DataStorageHBasePreparer {
    private static final Logger logger = LoggerFactory.getLogger(DataStorageHBasePreparer.class);

    private final Admin admin;

    /**
     * Creates instance of DataStorageHBasePreparer with specific connection configuration.
     * @param configuration HBase connection configuration
     * @throws IOException if i/o error occurs while connecting to HBase
     */
    public DataStorageHBasePreparer(Configuration configuration) throws IOException {
        Connection connection = ConnectionFactory.createConnection(configuration);
        this.admin = connection.getAdmin();
    }

    @PostConstruct
    public void initializeTables() throws IOException {
        createMailTable();
    }

    private void createMailTable() throws IOException {
        TableName mailTableName = TableName.valueOf("mail");
        HTableDescriptor table = new HTableDescriptor(mailTableName);
        table.addFamily(new HColumnDescriptor("raw"));
        table.addFamily(new HColumnDescriptor("general"));
        table.addFamily(new HColumnDescriptor("artifact"));
        table.addFamily(new HColumnDescriptor("feature"));
        createTableIfNeeded(mailTableName, table);
    }

    private void createTableIfNeeded(TableName tableName, HTableDescriptor table) throws IOException {
        if (!admin.tableExists(tableName)) {
            admin.createTable(table);
            logger.info("New table {} created", tableName.getNameAsString());
        }
    }
}
