package com.demo.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    private static final String MAIN_SERVICE = "service_1";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/circuit-breaker")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "testFallBack")
    public ResponseEntity<String> circuitBreaker() {
        LOGGER.info("I'm here in main service calling service one");
        String response = restTemplate.getForObject("http://localhost:8081/serviceOne", String.class);
        return new ResponseEntity<String>(response, HttpStatus.OK);

    }


    private ResponseEntity<String> testFallBack(Exception e) {
        LOGGER.info("I'm here in main service calling service TWO");
        return new ResponseEntity<String>("In fallback method", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/bulk-head")
    @ResponseStatus(HttpStatus.OK)
    @Bulkhead(name = MAIN_SERVICE)
    public ResponseEntity<String> bulkHead() {
        String response = "hey!! I am in Bulk Head";
        return new ResponseEntity<String>(response, HttpStatus.OK);

    }
}
