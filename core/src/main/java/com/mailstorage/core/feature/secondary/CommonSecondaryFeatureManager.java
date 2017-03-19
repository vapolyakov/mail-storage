package com.mailstorage.core.feature.secondary;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.core.feature.secondary.accumulator.UserAccumulatedData;
import com.mailstorage.data.mail.entities.feature.secondary.SecondaryFeature;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author metal
 *
 * Allows to calculate and save all secondary features for specific user and period of time.
 * @param <M> secondary feature manager class that controls features extraction and saving
 */
public class CommonSecondaryFeatureManager<M extends SecondaryFeatureManager> {
    private static final Logger logger = LoggerFactory.getLogger(CommonSecondaryFeatureManager.class);

    private List<M> managers;
    private AbstractHBDAO<Long, SecondaryFeature> secondaryFeatureDao;

    /**
     * Instantiates CommonSecondaryFeatureManager.
     * @param managers available secondary feature managers
     * @param secondaryFeatureDao base secondary feature dao for dealing with general information about accumulated data
     */
    public CommonSecondaryFeatureManager(List<M> managers, AbstractHBDAO<Long, SecondaryFeature> secondaryFeatureDao) {
        this.managers = managers;
        this.secondaryFeatureDao = secondaryFeatureDao;
    }

    /**
     * Calculate all available secondary features for given user accumulated data.
     * @param input accumulated data for specific user and period of time
     * @return id of secondary features row
     */
    public Long calculateFeatures(UserAccumulatedData input) {
        long id = Instant.now().getMillis();
        try {
            logger.info("Starting to save base information of secondary features for user {} from {} to {}",
                    input.getUserId(), input.getFrom(), input.getTo());

            SecondaryFeature secondaryFeature = new SecondaryFeature(id,
                    input.getUserId(), "mail", input.getFrom(), input.getTo());
            secondaryFeatureDao.persist(secondaryFeature);
        } catch (Exception e) {
            logger.error("Saving failed", e);
            throw new RuntimeException(e);
        }

        managers.stream().forEach(manager -> manager.extractAndSave(id, input));

        return id;
    }
}
