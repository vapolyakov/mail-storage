package com.mailstorage.core.primary;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.core.artifact.extractors.AttachmentCountArtifactExtractor;
import com.mailstorage.core.artifact.extractors.OrclWordArtifactExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author metal
 */
public class PrimaryEntitiesRegistryTest {
    private static final Mail mail = CoreTestData.getMail();
    private static final OrclWordArtifactExtractor orclExtractor = new OrclWordArtifactExtractor();
    private static final AttachmentCountArtifactExtractor attachCountExtractor = new AttachmentCountArtifactExtractor();

    @Test
    public void testRegistryRegisterAndGet() {
        PrimaryEntitiesRegistry registry = new PrimaryEntitiesRegistry();
        registry.registerPrimaryEntity(orclExtractor.extract(mail));
        registry.registerPrimaryEntity(attachCountExtractor.extract(mail));

        checkArtifacts(registry);
    }

    @Test
    public void testRegistryMerge() {
        PrimaryEntitiesRegistry registry1 = new PrimaryEntitiesRegistry();
        registry1.registerPrimaryEntity(orclExtractor.extract(mail));

        PrimaryEntitiesRegistry registry2 = new PrimaryEntitiesRegistry();
        registry2.registerPrimaryEntity(attachCountExtractor.extract(mail));

        registry1.merge(registry2);

        checkArtifacts(registry1);
    }

    private void checkArtifacts(PrimaryEntitiesRegistry registry) {
        OrclWordArtifact orcl = registry.get(OrclWordArtifact.class);
        Assert.assertEquals(18, orcl.getPos().intValue());

        AttachmentCountArtifact count = registry.get(AttachmentCountArtifact.class);
        Assert.assertEquals(1, count.getCount().intValue());
    }
}
