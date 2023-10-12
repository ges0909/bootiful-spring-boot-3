package com.valantic.sti.gofastwithspringboot31;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.*;

@TestConfiguration(proxyBeanMethods = false)
public class BootifulSpringBoot3ApplicationTest {

    @Bean
    @RestartScope // says 'devtools' not to re-create this bean on each application re-build
    @ServiceConnection // derives url, username and password from docker container automatically
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
//                .withDatabaseName("test_db")
//                .withUsername("springboot3")
//                .withPassword("springboot3demopassword");
    }

    public static void main(final String[] args) {
        SpringApplication
                .from(BootifulSpringBoot3Application::main)
                .with(BootifulSpringBoot3ApplicationTest.class)
                .run(args);
    }
}
