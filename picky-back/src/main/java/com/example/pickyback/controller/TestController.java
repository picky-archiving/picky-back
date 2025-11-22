package com.example.pickyback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Hello World";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
