package com.mailstorage.core.artifact;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;
import com.mailstorage.data.mail.entities.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author metal
 */
public abstract class BaseArtifactManager<T extends BasePrimaryEntity> {
    private static final Logger logger = LoggerFactory.getLogger(BaseArtifactManager.class);

    protected BaseArtifactExtractor<T> extractor;
    protected AbstractHBDAO<Long, T> dao;

    public BaseArtifactManager(BaseArtifactExtractor<T> extractor, AbstractHBDAO<Long, T> dao) {
        this.extractor = extractor;
        this.dao = dao;
    }

    public T extractAndSave(Mail mail) {
        logger.info("Starting to extract and save artifact with manager {}", this.getClass().getSimpleName());
        try {
            T result = extractor.extract(mail);
            logger.info("Extracting finished successfully");
            if (result != null) {
                logger.info("Persisting artifact into hbase");
                dao.persist(result);
                logger.info("Successfully persisted for id {}", result.getTimestamp());
            }
            return result;
        } catch (Exception e) {
            logger.error("Operation failed", e);
            throw new RuntimeException(e);
        }
    }
}
