package com.mailstorage.data.mail.dao.artifact;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

/**
 * @author metal
 */
public class SubjectArtifactDao extends AbstractHBDAO<Long, SubjectArtifact> {
    public SubjectArtifactDao(Configuration conf) throws IOException {
        super(conf);
    }
}
