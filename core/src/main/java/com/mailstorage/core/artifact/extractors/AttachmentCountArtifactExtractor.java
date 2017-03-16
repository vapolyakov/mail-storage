package com.mailstorage.core.artifact.extractors;

import com.mailstorage.core.primary.PrimaryEntityExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;

/**
 * @author metal
 */
public class AttachmentCountArtifactExtractor implements PrimaryEntityExtractor<Mail, AttachmentCountArtifact> {
    @Override
    public AttachmentCountArtifact extract(Mail mail) {
        return new AttachmentCountArtifact(mail.getTimestamp(), mail.getAttachments().size());
    }
}
