package com.mailstorage.core.artifact;

import com.mailstorage.data.mail.entities.Mail;

import java.util.List;

/**
 * @author metal
 */
public class CommonArtifactManager {
    private List<BaseArtifactManager> artifactManagers;

    public CommonArtifactManager(List<BaseArtifactManager> artifactManagers) {
        this.artifactManagers = artifactManagers;
    }

    public void calculateArtifacts(Mail mail) {
        artifactManagers.stream().forEach(manager -> manager.extractAndSave(mail));
    }
}
