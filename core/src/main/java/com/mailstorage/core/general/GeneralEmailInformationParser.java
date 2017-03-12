package com.mailstorage.core.general;

import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.raw.RawFileInfo;
import org.apache.commons.mail.util.MimeMessageParser;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author metal
 */
public class GeneralEmailInformationParser {
    private static final Logger logger = LoggerFactory.getLogger(GeneralEmailInformationParser.class);

    public static Mail parse(RawFileInfo rawEmailFileInfo, String hdfsId) throws Exception {
        logger.info("Parsing email from local file");

        MimeMessageParser parser = getMimeMessageParser(rawEmailFileInfo);
        Mail result = getMail(rawEmailFileInfo, hdfsId, parser);

        logger.info("Email successfully parsed");
        return result;
    }

    private static Mail getMail(RawFileInfo rawEmailFileInfo, String hdfsId, MimeMessageParser parser) throws Exception {
        Mail result = new Mail(
                Instant.now().getMillis(),
                rawEmailFileInfo.getUserId(), rawEmailFileInfo.getInitialFileName(), hdfsId,
                parser.getFrom(),
                toStringList(parser.getTo()),
                toStringList(parser.getCc()),
                toStringList(parser.getBcc()),
                parser.getSubject(),
                parser.getPlainContent(),
                toContentTypeByNameMap(parser.getAttachmentList())
        );

        result.setEmailLocalFile(rawEmailFileInfo.getData());
        result.setAttachmentsData(parser.getAttachmentList());
        return result;
    }

    private static MimeMessageParser getMimeMessageParser(RawFileInfo rawEmailFileInfo) throws Exception {
        InputStream input = new FileInputStream(rawEmailFileInfo.getData());
        MimeMessage emailMessage = new MimeMessage(null, input);
        return new MimeMessageParser(emailMessage).parse();
    }

    private static List<String> toStringList(List<Address> addresses) {
        return addresses.stream().map(Address::toString).collect(Collectors.toList());
    }

    private static Map<String, String> toContentTypeByNameMap(List<DataSource> attachments) {
        return attachments.stream().collect(Collectors.toMap(DataSource::getName, DataSource::getContentType));
    }
}
