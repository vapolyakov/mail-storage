package com.mailstorage.core;

import com.mailstorage.core.artifact.extractors.AttachmentCountArtifactExtractor;
import com.mailstorage.core.artifact.extractors.OrclWordArtifactExtractor;
import com.mailstorage.core.artifact.extractors.SberWordArtifactExtractor;
import com.mailstorage.core.artifact.extractors.SubjectArtifactExtractor;
import com.mailstorage.core.general.GeneralEmailInformationParser;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.core.primary.PrimaryEntityExtractor;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.raw.RawFileInfo;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * @author metal
 */
public class CoreTestData {
    public static final String USER_ID = "123";
    public static final String USER_ID_2 = "567";
    public static final String HDFS_ID = "hdfs_id_1";
    public static final String FILENAME = "test_mail.eml";
    public static final File EMAIL_FILE;
    public static final RawFileInfo RAW_EMAIL_FILE_INFO;

    public static final String FRAUD_EMAILS = "Olo Oloev <olol@ololol.mail.mail>";
    public static final String SUSPICIOUS_WORDS = "money, fraud, banks";

    private static Mail mail;
    private static PrimaryEntitiesRegistry registry;
    private static List<PrimaryEntityExtractor<Mail, ?>> artifactExtractors;

    static {
        try {
            EMAIL_FILE = new File(CoreTestData.class.getResource(FILENAME).toURI());
            RAW_EMAIL_FILE_INFO = new RawFileInfo(USER_ID, FILENAME, EMAIL_FILE);

            artifactExtractors = Arrays.asList(
                    new OrclWordArtifactExtractor(),
                    new SberWordArtifactExtractor(),
                    new AttachmentCountArtifactExtractor(),
                    new SubjectArtifactExtractor(FRAUD_EMAILS, SUSPICIOUS_WORDS)
            );
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Mail getMail() {
        if (mail == null) {
            mail = GeneralEmailInformationParser.parse(RAW_EMAIL_FILE_INFO, HDFS_ID);
        }
        return mail;
    }

    public static PrimaryEntitiesRegistry getExtractedEntitiesRegistry() {
        if (registry == null) {
            registry = new PrimaryEntitiesRegistry();
            registry.registerPrimaryEntity(getMail());
            artifactExtractors.stream().forEach(extractor -> registry.registerPrimaryEntity(extractor.extract(getMail())));
        }
        return registry;
    }
}
