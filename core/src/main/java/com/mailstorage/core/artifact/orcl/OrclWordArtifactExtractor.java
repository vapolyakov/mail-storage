package com.mailstorage.core.artifact.orcl;

import com.mailstorage.core.artifact.BaseArtifactExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.OrclWordArtifact;

/**
 * @author metal
 */
public class OrclWordArtifactExtractor implements BaseArtifactExtractor<OrclWordArtifact> {
    private static final String ORCL_WORD = "ORCL";

    @Override
    public OrclWordArtifact extract(Mail mail) {
        int pos = mail.getMessage().indexOf(ORCL_WORD);
        return pos < 0 ? null : new OrclWordArtifact(mail.getTimestamp(), pos);
    }
}
