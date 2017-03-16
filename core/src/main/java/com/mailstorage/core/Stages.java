package com.mailstorage.core;

import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.core.feature.primary.BaseFeatureManager;
import com.mailstorage.core.general.GeneralEmailInformationManager;
import com.mailstorage.core.primary.CommonPrimaryEntityManager;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.raw.RawFileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author metal
 */
public class Stages {
    private static final Logger logger = LoggerFactory.getLogger(Stages.class);

    private ThreadPoolExecutor generalInformationExtractorExecutor;
    private GeneralEmailInformationManager generalInformationManager;

    private ThreadPoolExecutor artifactExtractorExecutor;
    private CommonPrimaryEntityManager<Mail, BaseArtifactManager> commonArtifactManager;

    private ThreadPoolExecutor featureExtractorExecutor;
    private CommonPrimaryEntityManager<PrimaryEntitiesRegistry, BaseFeatureManager> commonFeatureManager;

    public Stages(ThreadPoolExecutor generalInformationExtractorExecutor,
            GeneralEmailInformationManager generalInformationManager,
            ThreadPoolExecutor artifactExtractorExecutor,
            CommonPrimaryEntityManager<Mail, BaseArtifactManager> commonArtifactManager,
            ThreadPoolExecutor featureExtractorExecutor,
            CommonPrimaryEntityManager<PrimaryEntitiesRegistry, BaseFeatureManager> commonFeatureManager)
    {
        this.generalInformationExtractorExecutor = generalInformationExtractorExecutor;
        this.generalInformationManager = generalInformationManager;
        this.artifactExtractorExecutor = artifactExtractorExecutor;
        this.commonArtifactManager = commonArtifactManager;
        this.featureExtractorExecutor = featureExtractorExecutor;
        this.commonFeatureManager = commonFeatureManager;
    }

    public void extractGeneralInfo(RawFileInfo rawEmailFileInfo, String hdfsId, boolean continueProcessing) {
        logger.info("Scheduling extract general info task for file {}:{}",
                rawEmailFileInfo.getUserId(), rawEmailFileInfo.getData().getAbsolutePath());

        generalInformationExtractorExecutor.submit(() -> {
            try {
                Mail mail = generalInformationManager.extractAndSaveGeneralInfo(rawEmailFileInfo, hdfsId);
                if (continueProcessing) {
                    extractArtifacts(mail, true);
                }
            } catch (Exception e) {
                logger.error("General info extracting failed", e);
            }
        });
    }

    public void extractArtifacts(Mail mail, boolean continueProcessing) {
        logger.info("Scheduling extract artifacts task for mail {}", mail.getHdfsId());

        artifactExtractorExecutor.submit(() -> {
            try {
                PrimaryEntitiesRegistry registry = commonArtifactManager.calculateEntities(mail);
                if (continueProcessing) {
                    extractPrimaryFeatures(registry, mail);
                }
            } catch (Exception e) {
                logger.error("Artifacts extracting failed", e);
            }
        });
    }

    public void extractPrimaryFeatures(PrimaryEntitiesRegistry registry, Mail mail) {
        final String id = mail.getTimestamp() + ":" + mail.getHdfsId();
        logger.info("Scheduling extract features task for mail {}", id);

        registry.registerPrimaryEntity(mail);
        featureExtractorExecutor.submit(() -> {
            try {
                commonFeatureManager.calculateEntities(registry);
            } catch (Exception e) {
                logger.error("Feature extracting failed", e);
            }
            logger.info("All processing finished for mail {}", id);
        });
    }
}
