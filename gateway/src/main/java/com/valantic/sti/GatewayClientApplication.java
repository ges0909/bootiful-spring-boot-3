package com.valantic.sti;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.gateway.route.*;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.*;
import org.springframework.web.reactive.function.client.support.*;
import org.springframework.web.service.annotation.*;
import org.springframework.web.service.invoker.*;

import reactor.core.publisher.Flux;

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
                        .filters(fs -> fs
                                        .setPath("/customers")
                                        .addResponseHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                                        .retry(10)
                                // .requestRateLimiter()
                                // .jsonToGRPC()
                                // .circuitBreaker()
                        ) // swap out 'proxy'
                        .uri("http://localhost:8080/")
                )
                .build();
    }

    @Bean
    ApplicationRunner applicationRunner(CustomerHttpClient http) {
        return a -> http.customers().subscribe(System.out::println);
    }

    @Bean
    CustomerHttpClient client(WebClient.Builder builder) {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(
                        builder.baseUrl("http://localhost:8080").build()
                ))
                .build()
                .createClient(CustomerHttpClient.class);
    }

    // generic clients
    interface CustomerHttpClient {
        @GetExchange("/customers")
        Flux<Customer> customers();

        @GetExchange("/customers/{name}")
        Flux<Customer> customersByName(@PathVariable String name);
    }

    record Customer(Long id, String name) {
    }
}
