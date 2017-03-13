package com.mailstorage.data.mail.dao.artifact;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

/**
 * @author metal
 */
public class AttachmentCountArtifactDao extends AbstractHBDAO<Long, AttachmentCountArtifact> {
    public AttachmentCountArtifactDao(Configuration conf) throws IOException {
        super(conf);
    }
}
