package com.digiwardrobe.configurations.utils;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.digiwardrobe.configurations.CustomUserDetails;

public final class AuthUtils {

    private AuthUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static UUID getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        final CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }
}
