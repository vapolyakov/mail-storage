package com.mailstorage.core.feature.primary.extractors;

import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.core.primary.PrimaryEntityExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.feature.primary.LengthFeature;

/**
 * @author metal
 */
public class LengthFeatureExtractor implements PrimaryEntityExtractor<PrimaryEntitiesRegistry, LengthFeature> {
    @Override
    public LengthFeature extract(PrimaryEntitiesRegistry input) {
        Mail mail = input.get(Mail.class);
        return mail == null
                ? null
                : new LengthFeature(mail.getTimestamp(), mail.getEmailLocalFile().length());
    }
}
