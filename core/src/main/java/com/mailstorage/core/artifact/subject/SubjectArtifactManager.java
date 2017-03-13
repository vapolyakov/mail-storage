package com.mailstorage.core.artifact.subject;

import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.data.mail.dao.artifact.SubjectArtifactDao;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;

/**
 * @author metal
 */
public class SubjectArtifactManager extends BaseArtifactManager<SubjectArtifact> {
    public SubjectArtifactManager(SubjectArtifactExtractor extractor, SubjectArtifactDao dao) {
        super(extractor, dao);
    }
}
