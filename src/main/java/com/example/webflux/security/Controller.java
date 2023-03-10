package com.example.webflux.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
public class Controller {

    @Autowired
    private ReactiveUserDetailsService userDetailsService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @ResponseBody
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        return Mono.just(authRequest)
                .flatMap(auth -> userDetailsService.findByUsername(authRequest.getUsername()))
                .filter(userDetails -> passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtService.generate(userDetails))))
                .switchIfEmpty(Mono.error(new AccessDeniedException("Unauthorized user.")));
    }

}