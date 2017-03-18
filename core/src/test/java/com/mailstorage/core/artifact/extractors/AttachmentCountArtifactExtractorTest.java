package com.mailstorage.core.artifact.extractors;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author metal
 */
public class AttachmentCountArtifactExtractorTest {
    private AttachmentCountArtifactExtractor extractor;

    @Before
    public void initialize() {
        extractor = new AttachmentCountArtifactExtractor();
    }

    @Test
    public void attachmentCountExtractionTest() {
        Mail mail = CoreTestData.getMail();
        AttachmentCountArtifact result = extractor.extract(mail);

        Assert.assertEquals(1, result.getCount().intValue());
    }
}
