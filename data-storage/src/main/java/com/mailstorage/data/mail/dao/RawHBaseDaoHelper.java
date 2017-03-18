package com.mailstorage.data.mail.dao;

import com.flipkart.hbaseobjectmapper.HBObjectMapper;
import com.flipkart.hbaseobjectmapper.codec.DeserializationException;
import org.apache.hadoop.hbase.client.Result;

/**
 * @author metal
 */
public class RawHBaseDaoHelper {
    private final HBObjectMapper hbObjectMapper;

    public RawHBaseDaoHelper(HBObjectMapper hbObjectMapper) {
        this.hbObjectMapper = hbObjectMapper;
    }

    public Object read(Result result, Class aClass) throws DeserializationException {
        return hbObjectMapper.readValue(result, aClass);
    }
}
