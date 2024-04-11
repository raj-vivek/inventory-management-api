package com.ladyshopee.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public String getHealth(){
        System.out.println("health");
        return "UP";
    }

    @GetMapping("/deep-health")
    public String getDeepHealth(){
        return "Deep Health: UP";
    }
}
