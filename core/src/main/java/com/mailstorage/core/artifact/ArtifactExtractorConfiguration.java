package com.mailstorage.core.artifact;

import com.mailstorage.core.artifact.orcl.OrclWordArtifactExtractor;
import com.mailstorage.core.artifact.orcl.OrclWordArtifactManager;
import com.mailstorage.data.mail.dao.OrclWordArtifactDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author metal
 */
@Configuration
public class ArtifactExtractorConfiguration {
    @Bean
    public OrclWordArtifactManager orclWordArtifactManager(OrclWordArtifactDao dao) {
        return new OrclWordArtifactManager(new OrclWordArtifactExtractor(), dao);
    }

    @Bean
    public CommonArtifactManager commonArtifactManager(List<BaseArtifactManager> managers) {
        return new CommonArtifactManager(managers);
    }
}
