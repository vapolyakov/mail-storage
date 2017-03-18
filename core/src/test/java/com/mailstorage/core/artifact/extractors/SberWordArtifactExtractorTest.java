package com.mailstorage.core.artifact.extractors;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.SberWordArtifact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author metal
 */
public class SberWordArtifactExtractorTest {
    private SberWordArtifactExtractor extractor;

    @Before
    public void initialize() {
        extractor = new SberWordArtifactExtractor();
    }

    @Test
    public void sberWordExtractionTest() {
        Mail mail = CoreTestData.getMail();
        SberWordArtifact result = extractor.extract(mail);

        Assert.assertNull(result);
    }
}
