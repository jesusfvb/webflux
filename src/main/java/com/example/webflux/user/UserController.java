package com.example.webflux.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
        return userService.getById(id);
    }


    @DeleteMapping()
    private Mono<Void> deleteAllById(@RequestBody List<String> ids) {
        return userService.deleteAllById(ids);
    }


    @PostMapping()
    public Mono<User> save(@RequestBody UserDto user) {
        return userService.save(user);
    }

    @PutMapping
    public Mono<User> update(@RequestBody User user) {
        return userService.update(user);
    }
}
