package com.example.application.user.support;

import com.example.application.exception.UserNotFoundException;
import com.example.application.user.User;
import com.example.application.user.UserRepository;
import com.example.application.user.mapper.UserCredentialsDtoMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserContextSupport {

    private UserRepository userRepository;

    public UserContextSupport(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserFromContext() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(UserNotFoundException::new);
    }

    public boolean hasCurrentUserRole(String role) {
        User user = findUserFromContext();
        return UserCredentialsDtoMapper.mapRolesToStrings(user.getRoles()).contains(role);
    }
}
