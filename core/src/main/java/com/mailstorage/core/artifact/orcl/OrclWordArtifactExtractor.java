package com.mailstorage.core.artifact.orcl;

import com.mailstorage.core.artifact.word.WordArtifactExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;

/**
 * @author metal
 */
public class OrclWordArtifactExtractor extends WordArtifactExtractor<OrclWordArtifact> {
    private static final String ORCL_WORD = "ORCL";

    public OrclWordArtifactExtractor() {
        super(ORCL_WORD);
    }

    @Override
    protected OrclWordArtifact getWordArtifact(Mail mail, int pos) {
        return new OrclWordArtifact(mail.getTimestamp(), pos);
    }
}
