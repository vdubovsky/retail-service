package io.vdubovsky.retailrules.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServicesConfiguration {

    @Bean
    public ExecutorService droolsExecutorService() {
        return Executors.newCachedThreadPool();
    }
}
