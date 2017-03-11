package com.mailstorage.utils.timestamp;

import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author metal
 */
public class CurrentDateProviderTest {
    @Test
    public void testFixedDate() {
        Assert.assertEquals("2017-03-11", CurrentDateProvider.getDate(Instant.parse("2017-03-11T10:20:30.040")));
    }
}
