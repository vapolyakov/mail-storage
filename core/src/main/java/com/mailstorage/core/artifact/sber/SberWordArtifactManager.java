package com.mailstorage.core.artifact.sber;

import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.data.mail.dao.artifact.SberWordArtifactDao;
import com.mailstorage.data.mail.entities.artifact.SberWordArtifact;

/**
 * @author metal
 */
public class SberWordArtifactManager extends BaseArtifactManager<SberWordArtifact> {
    public SberWordArtifactManager(SberWordArtifactExtractor extractor, SberWordArtifactDao dao) {
        super(extractor, dao);
    }
}
