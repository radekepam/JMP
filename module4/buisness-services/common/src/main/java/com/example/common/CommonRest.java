package com.example.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommonRest {

    @GetMapping("")
    public String hello() {
        return "Hello from common";
    }
}
