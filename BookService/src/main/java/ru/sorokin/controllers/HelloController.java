package ru.sorokin.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(required = false, defaultValue = "Мир") String name) {
        return new StringBuilder("Hello " + name).toString();
    }
}
