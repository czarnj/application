package com.example.application.user.dto;

import com.example.application.user.UserRole;

import java.util.Set;

public record UserRoleChangeDto(long id, Set<UserRole> roles) {
}
