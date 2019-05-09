package com.techprimers.security.jwtsecurity.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/rest/hello")
    public String hello() {
        return "Hello World";
    }
    
    @GetMapping("/hello2")
    public String hello2() {
        return "Hello Worl2d";
    }
}
