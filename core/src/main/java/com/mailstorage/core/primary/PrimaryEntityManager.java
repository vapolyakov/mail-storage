package com.mailstorage.core.primary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;
import com.mailstorage.data.mail.entities.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author metal
 *
 * Allows to extract and save primary entity from some input data.
 * @param <I> input data type
 * @param <O> output entity type
 */
public abstract class PrimaryEntityManager<I, O extends BasePrimaryEntity> {
    private static final Logger logger = LoggerFactory.getLogger(PrimaryEntityManager.class);

    private PrimaryEntityExtractor<I, O> extractor;
    private AbstractHBDAO<Long, O> dao;

    /**
     * Creates PrimaryEntityManager.
     * @param extractor specific entity extractor
     * @param dao specific entity dao to save extracted data
     */
    public PrimaryEntityManager(PrimaryEntityExtractor<I, O> extractor, AbstractHBDAO<Long, O> dao) {
        this.extractor = extractor;
        this.dao = dao;
    }

    /**
     * Get primary entity type (basically it is artifact or feature) that this manager can handle with.
     * @return entity type that this manager can handle with
     */
    protected abstract EntityType getEntityType();

    /**
     * Extract BasePrimaryEntity from input data and save it with specific dao.
     * @param input input data
     * @return extracted entity
     */
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
