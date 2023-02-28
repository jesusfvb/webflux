package com.example.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class Example {

    @GetMapping()
    public Mono<String> hello() {
        return Mono.just("Hello, World!");
    }

    @GetMapping("{text}")
    public Mono<String> helloPath(@PathVariable String text) {
        return Mono.just("Hello, World!").map(s -> s + " " + text);
    }

    @GetMapping("/flux")
    public Flux<String> helloFLux() {
        return Flux.just(1, 2, 3, 4, 5).map(i -> "Hello, World! " + i);
    }

    @GetMapping("/flux/{text}")
    public Flux<String> helloFLuxPath(@PathVariable String text) {
        return Flux.just(1, 2, 3, 4, 5).map(i -> "Hello, World! " + i).map(s -> s + " " + text);
    }

}
