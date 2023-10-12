package com.valantic.sti.gofastwithspringboot31;

import org.springframework.ai.client.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

@SpringBootApplication
public class BootifulSpringBoot3Application {

    public static void main(final String[] args) {
        SpringApplication.run(BootifulSpringBoot3Application.class, args);
    }
}

@RestController
@ResponseBody
class JokeController {

    private final AiClient aiClient;

    JokeController(final AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @GetMapping("/joke")
    String jokes() {
        return aiClient.generate("tell me a joke about seasons");
    }
}

@RestController
@ResponseBody
class CustomerController {
    private final CustomerRepository customerRepository;

    private final ObservationRegistry observationRegistry;

    CustomerController(final CustomerRepository customerRepository, final ObservationRegistry observationRegistry) {
        this.customerRepository = customerRepository;
        this.observationRegistry = observationRegistry;
    }

    @GetMapping("/customers")
    Collection<Customer> customers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{name}")
    @ResponseStatus(HttpStatus.OK)
    Customer customerByName(@PathVariable final String name) {
        return Observation
                .createNotStarted("by-name", this.observationRegistry) // creating a metric ...
                .observe(() -> customerRepository // ... to be traced
                        .findByName(name)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer '" + name + "' not found"))
                );
    }
}

interface CustomerRepository extends ListCrudRepository<Customer, Long> {
    Optional<Customer> findByName(final String name);
}

record Customer(@Id Long id, String name) {
}
