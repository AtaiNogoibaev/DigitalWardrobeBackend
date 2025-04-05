package com.digiwardrobe.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.digiwardrobe.configurations.utils.JwtUtil;
import com.digiwardrobe.data_access.entity.UserEntity;
import com.digiwardrobe.data_access.repositories.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationService(
            final UserRepository userRepository,
            final PasswordEncoder passwordEncoder,
            final JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> register(final String email, final String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return Optional.empty();
        }
        final UserEntity user = new UserEntity(email, passwordEncoder.encode(password));
        userRepository.save(user);

        return Optional.of(jwtUtil.generateToken(user));
    }

    public String login(final String email, final String password) {
        final UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getHashedPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return jwtUtil.generateToken(user);
    }
}
