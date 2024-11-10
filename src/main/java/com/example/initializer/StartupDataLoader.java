package com.example.initializer;

import com.example.entity.Customer;
import com.example.respository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
@Slf4j
public class StartupDataLoader implements CommandLineRunner {
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        Customer customer1 = Customer.builder()
                .customerId("3LINE001")
                .firstName("Ayo")
                .surname("Lawanson")
                .build();

        Customer customer2 = Customer.builder()
                .customerId("3LINE002")
                .firstName("Ogun")
                .surname("Emmanuel")
                .build();

        customerRepository.saveAll(Arrays.asList(customer1, customer2));
        log.info("Bootstrapped data successfully created");
    }
}
