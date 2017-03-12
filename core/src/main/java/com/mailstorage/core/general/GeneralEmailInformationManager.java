package com.mailstorage.core.general;

import com.mailstorage.data.mail.dao.MailDao;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.raw.RawFileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author metal
 */
public class GeneralEmailInformationManager {
    private static final Logger logger = LoggerFactory.getLogger(GeneralEmailInformationManager.class);

    private final MailDao mailDao;

    public GeneralEmailInformationManager(MailDao mailDao) {
        this.mailDao = mailDao;
    }

    public Mail extractAndSaveGeneralInfo(RawFileInfo rawEmailFileInfo, String hdfsId) {
        logger.info("Starting to extract general info about email {}", rawEmailFileInfo.getData().getAbsolutePath());
        try {
            Mail result = GeneralEmailInformationParser.parse(rawEmailFileInfo, hdfsId);
            mailDao.persist(result);
            logger.info("General email info successfully persisted with id {}", result.getTimestamp());
            return result;
        } catch (Exception e) {
            logger.error("Extracting and saving general info failed", e);
            throw new RuntimeException(e);
        }
    }
}
