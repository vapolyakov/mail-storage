package com.mailstorage.core.artifact.attachement;

import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.data.mail.dao.artifact.AttachmentCountArtifactDao;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;

/**
 * @author metal
 */
public class AttachmentCountArtifactManager extends BaseArtifactManager<AttachmentCountArtifact> {
    public AttachmentCountArtifactManager(AttachmentCountArtifactExtractor extractor, AttachmentCountArtifactDao dao) {
        super(extractor, dao);
    }
}
