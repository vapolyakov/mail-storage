package com.mailstorage.core;

import com.mailstorage.core.artifact.BaseArtifactManager;
import com.mailstorage.core.feature.primary.BaseFeatureManager;
import com.mailstorage.core.general.GeneralEmailInformationManager;
import com.mailstorage.core.primary.CommonPrimaryEntityManager;
import com.mailstorage.core.primary.PrimaryEntitiesRegistry;
import com.mailstorage.data.mail.entities.Mail;
import com.mailstorage.data.raw.RawFileInfo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author metal
 *
 * Main email processing class. Allows to extract general info from raw files, schedule artifact and feature extraction
 * for specific mails in a single processing chain or separately.
 */
public class Stages {
    private static final Logger logger = LoggerFactory.getLogger(Stages.class);

    private ThreadPoolExecutor generalInformationExtractorExecutor;
    private GeneralEmailInformationManager generalInformationManager;

    private ThreadPoolExecutor artifactExtractorExecutor;
    private CommonPrimaryEntityManager<Mail, BaseArtifactManager> commonArtifactManager;

    private ThreadPoolExecutor featureExtractorExecutor;
    private CommonPrimaryEntityManager<PrimaryEntitiesRegistry, BaseFeatureManager> commonFeatureManager;

    private boolean removeLocalFiles = true;

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

    /**
     * Schedules general info extraction and saving for specific raw file that is already uploaded in HDFS.
     * @param rawEmailFileInfo raw info about file to process
     * @param hdfsId file id in HDFS
     * @param continueProcessing continue extracting some other data or execute this stage separately
     */
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

    /**
     * Schedules artifact extraction and saving for specific email.
     * @param mail extracted general info about email (with ot without actual local email file)
     * @param continueProcessing continue extracting some other data or execute this stage separately
     */
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

    /**
     * Schedules feature extraction and saving for specific email and it's artifacts.
     * @param registry registry with extracted artifacts for this email
     * @param mail extracted general info about email (with ot without actual local email file)
     */
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

            if (removeLocalFiles && mail.getEmailLocalFile() != null) {
                logger.info("Removing local .eml file {}", mail.getEmailLocalFile());
                FileUtils.deleteQuietly(mail.getEmailLocalFile());
            }
        });
    }

    void setRemoveLocalFiles(boolean removeLocalFiles) {
        this.removeLocalFiles = removeLocalFiles;
    }
}
