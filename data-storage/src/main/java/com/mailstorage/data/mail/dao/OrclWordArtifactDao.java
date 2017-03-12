package com.mailstorage.data.mail.dao;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.OrclWordArtifact;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

/**
 * @author metal
 */
public class OrclWordArtifactDao extends AbstractHBDAO<Long, OrclWordArtifact> {
    public OrclWordArtifactDao(Configuration conf) throws IOException {
        super(conf);
    }
}
