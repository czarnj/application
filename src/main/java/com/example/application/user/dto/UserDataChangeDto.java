package com.example.application.user.dto;

public record UserDataChangeDto(String firstName, String lastName, String oldPassword, String newPassword) {
}
