package com.mailstorage.data.mail.dao;

import com.flipkart.hbaseobjectmapper.HBObjectMapper;
import com.flipkart.hbaseobjectmapper.codec.DeserializationException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * @author metal
 *
 * Raw HBase DAO for communicating with database not in ORM level.
 */
public class RawHBaseDao {
    private static final Logger logger = LoggerFactory.getLogger(RawHBaseDao.class);

    private static final byte[] RAW_COLUMN_FAMILY_NAME = Bytes.toBytes("raw");
    private static final byte[] USER_ID_QUALIFIER = Bytes.toBytes("user_id");

    private final Connection connection;
    private final int numberOfRowsForCachingInScans;
    private final HBObjectMapper hbObjectMapper;

    public RawHBaseDao(Configuration configuration, int numberOfRowsForCachingInScans,
            HBObjectMapper hbObjectMapper) throws IOException
    {
        this.connection = ConnectionFactory.createConnection(configuration);
        this.numberOfRowsForCachingInScans = numberOfRowsForCachingInScans;
        this.hbObjectMapper = hbObjectMapper;
    }

    public Map<String, List<Object>> scan(String tableName, Map<String, List<String>> familyMap,
            long start, List<Class> entitiesToDeserialize) throws IOException
    {
        return scan(tableName, familyMap, start, Instant.now().getMillis(), entitiesToDeserialize);
    }

    public Map<String, List<Object>> scan(String tableName, Map<String, List<String>> familyMap,
            long start, long end, List<Class> entitiesToDeserialize) throws IOException
    {
        Scan scan = constructScan(familyMap, start, end);

        TableName mailTableName = TableName.valueOf(tableName);
        try (Table table = connection.getTable(mailTableName)) {
            try (ResultScanner resultScanner = table.getScanner(scan)) {
                Map<String, List<Object>> deserializedEntities = new HashMap<>();
                resultScanner.forEach(result -> {
                    String userId = getUserIdFromResult(result);

                    entitiesToDeserialize.forEach(aClass -> {
                        try {
                            if (!deserializedEntities.containsKey(userId)) {
                                deserializedEntities.put(userId, new ArrayList<>());
                            }
                            deserializedEntities.get(userId).add(hbObjectMapper.readValue(result, aClass));
                        } catch (DeserializationException e) {
                            logger.error("Deserialization of entity " + aClass.getSimpleName() + " failed", e);
                        }
                    });
                });

                return deserializedEntities;
            }
        }
    }

    private Scan constructScan(Map<String, List<String>> familyMap, long start, long end) {
        Scan scan = new Scan(Bytes.toBytes(start), Bytes.toBytes(end));

        scan.setCaching(numberOfRowsForCachingInScans);

        if (!familyMap.isEmpty()) {
            final Map<byte [], NavigableSet<byte []>> convertedMap =
                    new TreeMap<>(Bytes.BYTES_COMPARATOR);

            familyMap.forEach((family, qualifiers) -> {
                NavigableSet<byte []> qualifiersSet = new TreeSet<>(Bytes.BYTES_COMPARATOR);
                qualifiers.forEach(qualifier -> qualifiersSet.add(Bytes.toBytes(qualifier)));
                convertedMap.put(Bytes.toBytes(family), qualifiersSet);
            });

            scan.setFamilyMap(convertedMap);
        }

        scan.addColumn(RAW_COLUMN_FAMILY_NAME, USER_ID_QUALIFIER);

        return scan;
    }

    private String getUserIdFromResult(Result result) {
        return Bytes.toString(result.getValue(RAW_COLUMN_FAMILY_NAME, USER_ID_QUALIFIER));
    }
}
