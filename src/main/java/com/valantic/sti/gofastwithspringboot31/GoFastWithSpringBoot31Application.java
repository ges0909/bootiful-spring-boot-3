package com.valantic.sti.gofastwithspringboot31;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class GoFastWithSpringBoot31Application {

    public static void main(final String[] args) {
        SpringApplication.run(GoFastWithSpringBoot31Application.class, args);
    }
}

@Controller
@ResponseBody
class CustomerHttpController {
    private final CustomerRepository customerRepository;

    CustomerHttpController(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    Iterable<Customer> customers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{name}")
    Iterable<Customer> byName(@PathVariable final String name) {
        return customerRepository.findByName(name);
    }
}

interface CustomerRepository extends CrudRepository<Customer, Long> {
    Iterable<Customer> findByName(final String name);
}

record Customer(@Id Long id, String name) {
}
