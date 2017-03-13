package com.mailstorage.core.artifact.sber;

import com.mailstorage.core.artifact.word.WordArtifactExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.SberWordArtifact;

/**
 * @author metal
 */
public class SberWordArtifactExtractor extends WordArtifactExtractor<SberWordArtifact> {
    private static final String SBER_WORD = "sber";

    public SberWordArtifactExtractor() {
        super(SBER_WORD);
    }

    @Override
    protected SberWordArtifact getWordArtifact(Mail mail, int pos) {
        return new SberWordArtifact(mail.getTimestamp(), pos);
    }
}
