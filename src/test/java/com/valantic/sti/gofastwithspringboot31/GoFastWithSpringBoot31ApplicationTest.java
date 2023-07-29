package com.valantic.sti.gofastwithspringboot31;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class GoFastWithSpringBoot31ApplicationTest {

    @Bean
    @RestartScope
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:latest");
    }

    public static void main(final String[] args) {
        SpringApplication
                .from(GoFastWithSpringBoot31Application::main)
                .with(GoFastWithSpringBoot31ApplicationTest.class)
                .run(args);
    }
}
