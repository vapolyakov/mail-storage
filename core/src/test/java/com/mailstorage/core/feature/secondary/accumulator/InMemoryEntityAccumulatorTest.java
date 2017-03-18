package com.mailstorage.core.feature.secondary.accumulator;

import com.mailstorage.core.CoreTestData;
import com.mailstorage.data.mail.entities.artifact.OrclWordArtifact;
import com.mailstorage.data.mail.entities.artifact.SberWordArtifact;
import com.mailstorage.data.mail.entities.feature.LengthFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author metal
 */
public class InMemoryEntityAccumulatorTest {
    private InMemoryEntityAccumulator entityAccumulator;

    @Before
    public void initialize() {
        entityAccumulator = new InMemoryEntityAccumulator();
    }

    @Test
    public void inMemoryEntityAccumulatorTest() {
        entityAccumulator.put(CoreTestData.USER_ID, new OrclWordArtifact(1L, 1));
        entityAccumulator.put(CoreTestData.USER_ID, new OrclWordArtifact(4L, 4));
        entityAccumulator.put(CoreTestData.USER_ID, new SberWordArtifact(1L, 2));
        entityAccumulator.put(CoreTestData.USER_ID, new SberWordArtifact(4L, 5));
        entityAccumulator.put(CoreTestData.USER_ID, new LengthFeature(1L, 123L));
        entityAccumulator.put(CoreTestData.USER_ID, new LengthFeature(4L, 321L));

        entityAccumulator.put(CoreTestData.USER_ID_2, new OrclWordArtifact(2L, 21));
        entityAccumulator.put(CoreTestData.USER_ID_2, new SberWordArtifact(2L, 22));
        entityAccumulator.put(CoreTestData.USER_ID_2, new LengthFeature(2L, 2123L));
        entityAccumulator.put(CoreTestData.USER_ID_2, new OrclWordArtifact(3L, 25));
        entityAccumulator.put(CoreTestData.USER_ID_2, new SberWordArtifact(3L, 26));
        entityAccumulator.put(CoreTestData.USER_ID_2, new LengthFeature(3L, 225727L));

        Assert.assertEquals(12, entityAccumulator.currentSize());

        List<UserAccumulatedData> accumulatedForPeriod = entityAccumulator.getAccumulatedForPeriod(1L, 3L);
        Assert.assertEquals(2, accumulatedForPeriod.size());
        Assert.assertEquals(6, entityAccumulator.currentSize());

        UserAccumulatedData firstUserData = accumulatedForPeriod.get(0).getUserId().equals(CoreTestData.USER_ID)
                ? accumulatedForPeriod.get(0) : accumulatedForPeriod.get(2);

        Assert.assertEquals(3, firstUserData.getMappedByClassEntities().size());
        Assert.assertTrue(firstUserData.getMappedByClassEntities().containsKey(LengthFeature.class));

        Assert.assertEquals(2, firstUserData.getSpecificEntities(SberWordArtifact.class).get(0).getPos().intValue());
    }
}
