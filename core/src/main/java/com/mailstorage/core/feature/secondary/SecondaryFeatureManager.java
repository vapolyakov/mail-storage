package com.mailstorage.core.feature.secondary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.feature.secondary.accumulator.UserAccumulatedData;
import com.mailstorage.core.feature.secondary.extractors.SecondaryFeatureExtractor;
import com.mailstorage.data.mail.entities.feature.secondary.BaseSecondaryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author metal
 *
 * Allows to extract and save secondary features from accumulated input data.
 */
public class SecondaryFeatureManager<O extends BaseSecondaryEntity> {
    private static final Logger logger = LoggerFactory.getLogger(SecondaryFeatureManager.class);

    private SecondaryFeatureExtractor<O> extractor;
    private AbstractHBDAO<Long, O> dao;

    /**
     * Creates SecondaryFeatureManager.
     * @param extractor specific feature extractor
     * @param dao specific entity dao to save extracted data
     */
    public SecondaryFeatureManager(SecondaryFeatureExtractor<O> extractor, AbstractHBDAO<Long, O> dao) {
        this.extractor = extractor;
        this.dao = dao;
    }

    /**
     * Extract BaseSecondaryEntity from input data and save it with specific dao.
     * @param intervalId id of specific interval of time, user and data type, that this feature is calculated for
     * @param input input data
     * @return extracted feature
     */
    public O extractAndSave(Long intervalId, UserAccumulatedData input) {
        logger.info("Starting to extract and save secondary feature with {}", extractor.getClass().getSimpleName());
        try {
            O result = extractor.extract(intervalId, input);
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
