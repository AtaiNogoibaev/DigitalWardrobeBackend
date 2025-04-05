package com.digiwardrobe.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digiwardrobe.controllers.request_bodies.AuthBody;
import com.digiwardrobe.services.AuthenticationService;

/*
 * Controller for handling registration and login
 * 
 * Uses REST instead of GraphQL, as it makes little sense to include those 
 * as mutations, even though it is possible
*/
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody final AuthBody body) {
        final Optional<String> newUser = authenticationService.register(body.getEmail(), body.getPassword());
        return newUser.<ResponseEntity<?>>map(s -> ResponseEntity.ok(Map.of("token", s))).orElseGet(() -> ResponseEntity
                .badRequest()
                .body(Map.of("message", String.format("User %s already exists!", body.getEmail()))));
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody final AuthBody body) {
        final String token = authenticationService.login(body.getEmail(), body.getPassword());
        return Map.of("token", token);
    }
}
