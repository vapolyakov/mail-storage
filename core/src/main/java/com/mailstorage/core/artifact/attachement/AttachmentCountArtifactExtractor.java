package com.mailstorage.core.artifact.attachement;

import com.mailstorage.core.artifact.BaseArtifactExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;

/**
 * @author metal
 */
public class AttachmentCountArtifactExtractor implements BaseArtifactExtractor<AttachmentCountArtifact> {
    @Override
    public AttachmentCountArtifact extract(Mail mail) {
        return new AttachmentCountArtifact(mail.getTimestamp(), mail.getAttachments().size());
    }
}
