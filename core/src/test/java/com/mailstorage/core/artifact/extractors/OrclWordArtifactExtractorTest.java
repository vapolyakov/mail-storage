package com.mailstorage.core.artifact.extractors;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author metal
 */
public class OrclWordArtifactExtractorTest {
    private OrclWordArtifactExtractor extractor;

    @Before
    public void initialize() {
        extractor = new OrclWordArtifactExtractor();
    }

    @Test
    public void orclWordExtractionTest() {
        Mail mail = CoreTestData.getMail();
        OrclWordArtifact result = extractor.extract(mail);

        Assert.assertEquals(18, result.getPos().intValue());
    }
}
