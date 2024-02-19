package com.example.application.user.dto;

import java.util.Set;

public record UserCredentialsDto(String username, String password, Set<String> roles) {
}
