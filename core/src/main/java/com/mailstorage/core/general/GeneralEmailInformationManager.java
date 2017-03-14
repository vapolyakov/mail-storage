package com.mailstorage.core.general;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.raw.RawFileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author metal
 */
public class GeneralEmailInformationManager {
    private static final Logger logger = LoggerFactory.getLogger(GeneralEmailInformationManager.class);

    private final AbstractHBDAO<Long, Mail> mailDao;

    public GeneralEmailInformationManager(AbstractHBDAO<Long, Mail> mailDao) {
        this.mailDao = mailDao;
    }

    public Mail extractAndSaveGeneralInfo(RawFileInfo rawEmailFileInfo, String hdfsId) {
        logger.info("Starting to extract email general info for {}", rawEmailFileInfo.getData().getAbsolutePath());
        try {
            Mail result = GeneralEmailInformationParser.parse(rawEmailFileInfo, hdfsId);
            mailDao.persist(result);
            logger.info("Email info successfully persisted with id {}", result.getTimestamp());
            return result;
        } catch (Exception e) {
            logger.error("Operation failed", e);
            throw new RuntimeException(e);
        }
    }
}
