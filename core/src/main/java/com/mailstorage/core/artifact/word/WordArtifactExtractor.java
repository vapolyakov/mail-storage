package com.mailstorage.core.artifact.word;

import com.mailstorage.core.artifact.BaseArtifactExtractor;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;
import com.mailstorage.data.mail.entities.Mail;

/**
 * @author metal
 */
public abstract class WordArtifactExtractor<T extends BasePrimaryEntity> implements BaseArtifactExtractor<T> {
    private final String word;

    public WordArtifactExtractor(String word) {
        this.word = word;
    }

    @Override
    public T extract(Mail mail) {
        int pos = mail.getMessage().indexOf(word);
        return pos < 0 ? null : getWordArtifact(mail, pos);
    }

    protected abstract T getWordArtifact(Mail mail, int pos);
}
