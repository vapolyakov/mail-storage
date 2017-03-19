package com.mailstorage.core.feature.secondary.accumulator;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.data.mail.entities.artifact.AttachmentCountArtifact;
import com.mailstorage.data.mail.entities.artifact.SberWordArtifact;
import com.mailstorage.data.mail.entities.feature.primary.LengthFeature;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author metal
 */
public class UserAccumulatedDataTest {
    @Test
    public void testDataAccumulation() {
        UserAccumulatedData userAccumulatedData = new UserAccumulatedData(CoreTestData.USER_ID, 1, 2);
        userAccumulatedData.add(new LengthFeature(1L, 123L));
        userAccumulatedData.add(new SberWordArtifact(2L, 345));
        userAccumulatedData.add(new AttachmentCountArtifact(3L, 987));

        Assert.assertEquals(3, userAccumulatedData.getMappedByClassEntities().size());
        Assert.assertEquals(1, userAccumulatedData.getSpecificEntities(SberWordArtifact.class).size());
        Assert.assertEquals(987, userAccumulatedData
                .getSpecificEntities(AttachmentCountArtifact.class).get(0).getCount().intValue());
    }
}
