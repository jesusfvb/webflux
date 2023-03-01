package com.example.webflux.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Flux<User> getAll() {
        return userService.getAll();
    }

    @GetMapping({"/{id}"})
    public Mono<User> getById(@PathVariable String id) {
        return userService.getById(Long.parseLong(id));
    }

    @PostMapping("{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return userService.deleteById(Long.parseLong(id));
    }

    @PostMapping()
    public Mono<User> save(@RequestBody User user) {
        return userService.save(user);
    }

}
