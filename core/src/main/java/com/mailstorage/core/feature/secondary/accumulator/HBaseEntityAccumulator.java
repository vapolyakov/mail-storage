package com.mailstorage.core.feature.secondary.accumulator;

import com.mailstorage.data.mail.dao.RawHBaseDao;
import com.mailstorage.data.mail.entities.BasePrimaryEntity;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * @author metal
 *
 * Accumulator based on data already written in HBase. Empty put() method indicates that there is no actual
 * data storing, only reading from HBase.
 */
public class HBaseEntityAccumulator implements EntityAccumulator {
    private static final Logger logger = LoggerFactory.getLogger(HBaseEntityAccumulator.class);

    private static final String MAIL_TABLE = "mail";
    private static final String ARTIFACT_COLUMN_FAMILY = "artifact";
    private static final String FEATURE_COLUMN_FAMILY = "feature";

    private final RawHBaseDao rawHBaseDao;

    public HBaseEntityAccumulator(RawHBaseDao rawHBaseDao) {
        this.rawHBaseDao = rawHBaseDao;
    }

    @Override
    public void put(String userId, BasePrimaryEntity entity) { }

    @Override
    public List<UserAccumulatedData> getAccumulatedForPeriod(long start, long end) {
        try {
            Map<String, List<Object>> scanResult = rawHBaseDao
                    .scan(MAIL_TABLE, constructFamilyMap(), start, end, getAllAvailableEntities());

            List<UserAccumulatedData> result = new ArrayList<>();
            scanResult.forEach((userId, entities) -> {
                UserAccumulatedData userAccumulatedData = new UserAccumulatedData(userId, start, end);
                entities.forEach(entity -> {
                    BasePrimaryEntity basePrimaryEntity = (BasePrimaryEntity) entity;
                    if (basePrimaryEntity.isInitialized()) {
                        userAccumulatedData.add(basePrimaryEntity);
                    }
                });
                result.add(userAccumulatedData);
            });
            return result;
        } catch (IOException e) {
            logger.error("Collecting available features and artifacts for period "
                    + start + " : " + end + " failed", e);
            throw new RuntimeException(e);
        }
    }

    private Map<String, List<String>> constructFamilyMap() {
        Map<String, List<String>> familyMap = new HashMap<>();
        familyMap.put(ARTIFACT_COLUMN_FAMILY, new ArrayList<>());
        familyMap.put(FEATURE_COLUMN_FAMILY, new ArrayList<>());
        return familyMap;
    }

    private List<Class> getAllAvailableEntities() {
        Reflections reflections = new Reflections("com.mailstorage.data.mail.entities");
        Set<Class<? extends BasePrimaryEntity>> allEntityClasses = reflections.getSubTypesOf(BasePrimaryEntity.class);
        return new ArrayList<>(allEntityClasses);
    }
}
