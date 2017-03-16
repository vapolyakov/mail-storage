package com.mailstorage.core.primary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;
import com.mailstorage.data.mail.entities.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author metal
 */
public abstract class PrimaryEntityManager<I, O extends BasePrimaryEntity> {
    private static final Logger logger = LoggerFactory.getLogger(PrimaryEntityManager.class);

    private PrimaryEntityExtractor<I, O> extractor;
    private AbstractHBDAO<Long, O> dao;

    public PrimaryEntityManager(PrimaryEntityExtractor<I, O> extractor, AbstractHBDAO<Long, O> dao) {
        this.extractor = extractor;
        this.dao = dao;
    }

    protected abstract EntityType getEntityType();

    public O extractAndSave(I input) {
        logger.info("Starting to extract and save {}", getEntityType());
        try {
            O result = extractor.extract(input);
            logger.info("Extracting finished successfully");
            if (result != null) {
                logger.info("Persisting {} into hbase", result.getClass().getSimpleName());
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
