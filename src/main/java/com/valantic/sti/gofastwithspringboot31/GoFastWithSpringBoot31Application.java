package com.valantic.sti.gofastwithspringboot31;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

@SpringBootApplication
public class GoFastWithSpringBoot31Application {

    public static void main(final String[] args) {
        SpringApplication.run(GoFastWithSpringBoot31Application.class, args);
    }
}

@RestController
@ResponseBody
class CustomerController {
    private final CustomerRepository customerRepository;

    CustomerController(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    Collection<Customer> customers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{name}")
    @ResponseStatus(HttpStatus.OK)
    Customer findByName(@PathVariable final String name) {
        return customerRepository
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer '" + name + "' not found"));
    }
}

interface CustomerRepository extends ListCrudRepository<Customer, Long> {
    Optional<Customer> findByName(final String name);
}

record Customer(@Id Long id, String name) {
}
