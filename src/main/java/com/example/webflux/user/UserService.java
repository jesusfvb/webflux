package com.example.webflux.user;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    public Flux<User> data = Flux.just(
            new User(1L, "John", "john"),
            new User(2L, "Jane", "jane"),
            new User(3L, "Jack", "jack"),
            new User(4L, "Jill", "jill"),
            new User(5L, "Jenny", "jenny"),
            new User(6L, "Jen", "jen"),
            new User(7L, "Jenifer", "jenifer"),
            new User(8L, "Jeniffer", "jeniffer")
    );

    public Flux<User> getAll() {
        return data;
    }

    public Mono<User> getById(Long id) {
        return data.filter(user -> user.getId().equals(id)).single();
    }

    public Mono<Void> deleteById(Long id) {
        data = data.filter(user -> !user.getId().equals(id));
        return Mono.empty();
    }

    public Mono<User> save(User user) {
        Mono<User> userMono = Mono.just(user);
        data = data.concatWith(userMono);
        return userMono;
    }
}
