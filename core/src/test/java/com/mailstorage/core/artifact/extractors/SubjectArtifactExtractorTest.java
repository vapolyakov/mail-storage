package com.mailstorage.core.artifact.extractors;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.SubjectArtifact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.mailstorage.core.CoreTestData.FRAUD_EMAILS;
import static com.mailstorage.core.CoreTestData.SUSPICIOUS_WORDS;

/**
 * @author metal
 */
public class SubjectArtifactExtractorTest {
    private SubjectArtifactExtractor extractor;

    @Before
    public void initialize() {
        extractor = new SubjectArtifactExtractor(FRAUD_EMAILS, SUSPICIOUS_WORDS);
    }

    @Test
    public void subjectExtractionTest() {
        Mail mail = CoreTestData.getMail();
        SubjectArtifact result = extractor.extract(mail);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isFraudTo());
        Assert.assertNull(result.getSuspiciousWords());
    }
}
