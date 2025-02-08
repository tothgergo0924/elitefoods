package com.Bcsapat._ib153l14b.EliteFoods.restController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api")
public class TestRest {
    @GetMapping("/hello")
    public String sayHello(){
        log.info("rest meghivva!");
        return "Hello from Spring Boot!";
    }
}
