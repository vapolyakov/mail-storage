package com.mailstorage.core.general;

import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.raw.RawFileInfo;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author metal
 */
public class GeneralEmailInformationParserTest {
    private static final String USER_ID = "123";
    private static final String HDFS_ID = "hdfs_id_1";

    @Test
    public void testEmailInformationParsing() throws Exception {
        File email = new File(this.getClass().getResource("test_mail.eml").toURI());
        RawFileInfo rawEmailFileInfo = new RawFileInfo(USER_ID, "some.eml", email);

        Mail mail = GeneralEmailInformationParser.parse(rawEmailFileInfo, HDFS_ID);

        Assert.assertEquals(1, mail.getTo().size());
        Assert.assertEquals("Olo Oloev <olol@ololol.mail.mail>", mail.getTo().get(0));

        Assert.assertEquals(1, mail.getAttachments().size());
        Assert.assertEquals(1, mail.getAttachmentsData().size());

        Assert.assertEquals(USER_ID, mail.getUid());
        Assert.assertEquals(HDFS_ID, mail.getHdfsId());
    }
}
