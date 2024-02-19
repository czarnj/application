package com.example.application.user;

import com.example.application.exception.PasswordNotMatchException;
import com.example.application.exception.UserNotFoundException;
import com.example.application.user.dto.UserCredentialsDto;
import com.example.application.user.dto.UserDataChangeDto;
import com.example.application.user.dto.UserRegistrationDto;
import com.example.application.user.dto.UserRoleChangeDto;
import com.example.application.user.mapper.UserCredentialsDtoMapper;
import com.example.application.user.support.UserContextSupport;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final String USER_ROLE = "USER";
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private UserContextSupport userContextSupport;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder, UserContextSupport userContextSupport) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userContextSupport = userContextSupport;
    }

    public Optional<UserCredentialsDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserCredentialsDtoMapper::mapToUserCredentialsDto);
    }

    @Transactional
    public void register(UserRegistrationDto userToRegister) {
        User user = new User();
        user.setUsername(userToRegister.username());
        user.setPassword(passwordEncoder.encode(userToRegister.password()));
        user.setFirstName(userToRegister.firstName());
        user.setLastName(userToRegister.lastName());
        Optional<UserRole> userRole = userRoleRepository.findByName(USER_ROLE);
        userRole.ifPresentOrElse(
                role -> user.getRoles().add(role),
                () -> {
                    throw new NoSuchElementException();
                }
        );
        userRepository.save(user);
    }

    public void updateUser(UserDataChangeDto userDataChangeDto) throws UserNotFoundException {
        User user = findUserFromContext();
        changeUser(user, userDataChangeDto);
    }

    private void changeUser(User user, UserDataChangeDto userDataChangeDto) {
        if (userDataChangeDto.firstName() != null && !userDataChangeDto.firstName().isEmpty()) {
            user.setFirstName(userDataChangeDto.firstName());
        }
        if (userDataChangeDto.lastName() != null && !userDataChangeDto.lastName().isEmpty()) {
            user.setLastName(userDataChangeDto.lastName());
        }
        if (userDataChangeDto.oldPassword() != null && userDataChangeDto.newPassword() != null
                && !userDataChangeDto.oldPassword().isEmpty() && !userDataChangeDto.newPassword().isEmpty()) {
            if (passwordEncoder.matches(userDataChangeDto.oldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userDataChangeDto.newPassword()));
            }
            else {
                throw new PasswordNotMatchException();
            }
        }
        userRepository.save(user);
    }

    public List<User> findAllUsersWithoutCurrent() {
        List<User> users = userRepository.findAll();
        User currentUser = findUserFromContext();
        return users.stream()
                .filter(user -> user.getId() != currentUser.getId())
                .collect(Collectors.toList());
    }

    public User findUserFromContext() {
        return userContextSupport.findUserFromContext();
    }

    public boolean hasCurrentUserRole(String role) {
        return userContextSupport.hasCurrentUserRole(role);
    }

    public Set<UserRole> findAllRoles() {
        return new HashSet<>(userRoleRepository.findAll());
    }

    public void updateUserRoles(UserRoleChangeDto userWithChanges) {
        Optional<User> user = userRepository.findById(userWithChanges.id());
        user.ifPresent(u -> {
            u.setRoles(userWithChanges.roles());
            userRepository.save(u);
        });
    }
}
