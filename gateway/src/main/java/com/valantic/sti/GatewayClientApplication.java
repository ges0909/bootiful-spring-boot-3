package com.valantic.sti;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.gateway.route.*;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class GatewayClientApplication {

    public static void main(final String[] args) {
        SpringApplication.run(GatewayClientApplication.class, args);
    }

    @Bean
    RouteLocator gateway(RouteLocatorBuilder rlb) {
        return rlb
                .routes()
                .route(rs -> rs.path("/proxy")
                        .filters(fs -> fs.setPath("/customers"))
                        .uri("http://localhost:8080/")
                )
                .build();
    }
}
