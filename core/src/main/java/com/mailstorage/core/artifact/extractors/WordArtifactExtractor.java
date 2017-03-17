package com.mailstorage.core.artifact.extractors;

import com.mailstorage.core.primary.PrimaryEntityExtractor;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;
import com.mailstorage.data.mail.entities.Mail;

/**
 * @author metal
 *
 * Base class for all 'search specific word in email message body' artifact extractors.
 */
public abstract class WordArtifactExtractor<T extends BasePrimaryEntity> implements PrimaryEntityExtractor<Mail, T> {
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
