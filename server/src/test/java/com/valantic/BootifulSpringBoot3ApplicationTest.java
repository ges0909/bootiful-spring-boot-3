package com.valantic;

import org.springframework.boot.*;
import org.springframework.boot.devtools.restart.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.testcontainers.service.connection.*;
import org.springframework.context.annotation.*;

import org.testcontainers.containers.*;
import org.testcontainers.utility.*;

@TestConfiguration(proxyBeanMethods = false)
public class BootifulSpringBoot3ApplicationTest {

    public static void main(final String[] args) {
        SpringApplication
                .from(BootifulSpringBoot3Application::main)
                .with(BootifulSpringBoot3ApplicationTest.class)
                .run(args);
    }

    @Bean
    @RestartScope // says 'devtools' not to re-create this bean on each application re-build
    @ServiceConnection
        // derives url, username and password from docker container automatically
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
//                .withDatabaseName("test_db")
//                .withUsername("springboot3")
//                .withPassword("springboot3demopassword");
    }
}
