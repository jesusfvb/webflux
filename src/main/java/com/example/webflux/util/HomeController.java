package com.example.webflux.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    private Mono<String> getHome() {
        return Mono.just("Welcome to Webflux");
    }

    @GetMapping("{text}")
    private Mono<String> getHomePath(@PathVariable String text) {
        return Mono.just("Welcome to Webflux").map(s -> s + "  " + text);
    }

}
