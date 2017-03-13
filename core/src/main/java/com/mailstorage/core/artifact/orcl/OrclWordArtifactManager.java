package com.mailstorage.core.artifact.orcl;

import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.data.mail.dao.artifact.OrclWordArtifactDao;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;

/**
 * @author metal
 */
public class OrclWordArtifactManager extends BaseArtifactManager<OrclWordArtifact> {
    public OrclWordArtifactManager(OrclWordArtifactExtractor extractor, OrclWordArtifactDao dao) {
        super(extractor, dao);
    }
}
