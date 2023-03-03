package com.example.webflux.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    public Mono<User> save(UserDto user) {
        User userEntity = new User();
        userEntity.setName(user.getName());
        userEntity.setUsername(user.getUsername());
        return userRepository.save(userEntity);
    }

    public Mono<User> getById(String id) {
        return userRepository.findById(id);
    }

    public Mono<Void> deleteAllById(List<String> ids) {
        return userRepository.findAllById(ids).flatMap(userRepository::delete).then();
    }

    public Mono<User> update(User user) {
        return userRepository.findById(user.getId()).flatMap(entity -> {
            entity.setUsername(user.getUsername());
            entity.setName(user.getName());
            return userRepository.save(entity);
        });
    }
}
