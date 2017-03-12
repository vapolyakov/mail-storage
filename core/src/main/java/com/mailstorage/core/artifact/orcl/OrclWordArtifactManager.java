package com.mailstorage.core.artifact.orcl;

import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.data.mail.dao.OrclWordArtifactDao;
import com.mailstorage.data.mail.entities.OrclWordArtifact;

/**
 * @author metal
 */
public class OrclWordArtifactManager extends BaseArtifactManager<OrclWordArtifact> {
    public OrclWordArtifactManager(OrclWordArtifactExtractor extractor, OrclWordArtifactDao dao) {
        super(extractor, dao);
    }
}
