package com.mailstorage.core.general;

import com.mailstorage.data.mail.entities.Mail;
import org.junit.Assert;
import org.junit.Test;

import static com.mailstorage.core.CoreTestData.HDFS_ID;
import static com.mailstorage.core.CoreTestData.RAW_EMAIL_FILE_INFO;
import static com.mailstorage.core.CoreTestData.USER_ID;

/**
 * @author metal
 */
public class GeneralEmailInformationParserTest {

    @Test
    public void testEmailInformationParsing() throws Exception {
        Mail mail = GeneralEmailInformationParser.parse(RAW_EMAIL_FILE_INFO, HDFS_ID);

        Assert.assertEquals(1, mail.getTo().size());
        Assert.assertEquals("Olo Oloev <olol@ololol.mail.mail>", mail.getTo().get(0));

        Assert.assertEquals(1, mail.getAttachments().size());
        Assert.assertEquals(1, mail.getAttachmentsData().size());

        Assert.assertEquals(USER_ID, mail.getUid());
        Assert.assertEquals(HDFS_ID, mail.getHdfsId());
    }
}
