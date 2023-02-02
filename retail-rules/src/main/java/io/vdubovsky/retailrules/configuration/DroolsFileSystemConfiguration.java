package io.vdubovsky.retailrules.configuration;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConditionalOnProperty(value = "app.rules.source", havingValue = "file-system", matchIfMissing = true)
@Slf4j
public class DroolsFileSystemConfiguration {

    @Bean
    public KieServices kieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    public KieContainer kieContainer() {
        log.info("Loading rules from file system...");
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        File dir = new File(getClass().getClassLoader().getResource("rules").getPath());

        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                kieFileSystem.write(ResourceFactory.newFileResource(child));
            }
        } else {
            log.warn("There is no rules to load from file system");
        }
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        log.info("Rules loaded from file system, number of files: {}",
                directoryListing == null ? 0 : directoryListing.length);

        return kieContainer;
    }
}

