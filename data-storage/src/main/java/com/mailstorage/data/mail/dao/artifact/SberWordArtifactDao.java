package com.mailstorage.data.mail.dao.artifact;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.artifact.SberWordArtifact;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

/**
 * @author metal
 */
public class SberWordArtifactDao extends AbstractHBDAO<Long, SberWordArtifact> {
    public SberWordArtifactDao(Configuration conf) throws IOException {
        super(conf);
    }
}
