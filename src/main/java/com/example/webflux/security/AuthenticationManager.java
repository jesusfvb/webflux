package com.example.webflux.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

        @Autowired
        private JWTService jwtService;

        @Autowired
        private UserDetailsImplementation userDetailsImplementation;

        @Override
        public Mono<Authentication> authenticate(Authentication authentication) {
                return Mono.just(authentication.getCredentials().toString())
                                .flatMap(jwt -> Mono.just(jwtService.validate(jwt)))
                                .filter(valid -> valid)
                                .doOnError(error -> {
                                        throw new AccessDeniedException("Access Denied");
                                })
                                .switchIfEmpty(Mono.empty())
                                .flatMap(valid -> userDetailsImplementation.findByUsername(
                                                jwtService.getUsername(
                                                                authentication.getCredentials().toString())))
                                .map(userDetails -> new UsernamePasswordAuthenticationToken(
                                                userDetails.getUsername(),
                                                userDetails.getPassword(),
                                                userDetails.getAuthorities()));
        }
}
