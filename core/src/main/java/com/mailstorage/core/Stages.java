package com.mailstorage.core;

import com.mailstorage.core.artifact.CommonArtifactManager;
import com.mailstorage.core.general.GeneralEmailInformationManager;
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
    private CommonArtifactManager commonArtifactManager;

    public Stages(ThreadPoolExecutor generalInformationExtractorExecutor,
            GeneralEmailInformationManager generalInformationManager,
            ThreadPoolExecutor artifactExtractorExecutor,
            CommonArtifactManager commonArtifactManager)
    {
        this.generalInformationExtractorExecutor = generalInformationExtractorExecutor;
        this.generalInformationManager = generalInformationManager;
        this.artifactExtractorExecutor = artifactExtractorExecutor;
        this.commonArtifactManager = commonArtifactManager;
    }

    public void extractGeneralInfo(RawFileInfo rawEmailFileInfo, String hdfsId, boolean continueProcessing) {
        logger.info("Scheduling extract general info task for file {}:{}",
                rawEmailFileInfo.getUserId(), rawEmailFileInfo.getData().getAbsolutePath());

        generalInformationExtractorExecutor.submit(() -> {
            Mail mail = generalInformationManager.extractAndSaveGeneralInfo(rawEmailFileInfo, hdfsId);
            if (continueProcessing) {
                extractArtifacts(mail, true);
            }
        });
    }

    public void extractArtifacts(Mail mail, boolean continueProcessing) {
        logger.info("Scheduling extract artifacts task for mail {}", mail.getHdfsId());

        artifactExtractorExecutor.submit(() -> {
            commonArtifactManager.calculateArtifacts(mail);
            if (continueProcessing) {
                logger.info("Continue processing mail {}/{} and schedule feature extraction",
                        mail.getTimestamp(), mail.getHdfsId());
            }
        });
    }
}
