package com.example.application.user.mapper;

import com.example.application.user.User;
import com.example.application.user.UserRole;
import com.example.application.user.dto.UserCredentialsDto;

import java.util.Set;
import java.util.stream.Collectors;

public class UserCredentialsDtoMapper {
    public static UserCredentialsDto mapToUserCredentialsDto(User user) {
        return new UserCredentialsDto(user.getUsername(), user.getPassword(),
                mapRolesToStrings(user.getRoles()));
    }

    public static Set<String> mapRolesToStrings(Set<UserRole> roles) {
        return roles.stream()
                .map(UserRole::getName)
                .collect(Collectors.toSet());
    }
}
