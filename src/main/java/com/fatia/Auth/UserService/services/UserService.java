package com.fatia.Auth.UserService.services;

import com.fatia.Auth.UserService.config.JwtService;
import com.fatia.Auth.UserService.entities.Role;
import com.fatia.Auth.UserService.entities.User;
import com.fatia.Auth.UserService.entities.UserModel;
import com.fatia.Auth.UserService.repositories.UserRepository;
import com.fatia.Auth.UserService.requests.RegisterUserRequest;
import com.fatia.Auth.UserService.requests.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public List<UserModel> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();

        for (User user : users) {
            userModels.add(UserModel.toModel(user));
        }

        return userModels;
    }

    public void registerManager(RegisterUserRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MANAGER)
                .build();

        userRepository.save(user);
    }

    public void registerWorker(RegisterUserRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.WORKER)
                .build();

        userRepository.save(user);
    }

    public void updateUser(UpdateUserRequest request, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found"); // TODO Do custom exceptions
        }

        User user = optionalUser.get();

        if (request.getFirstName() != null)
            user.setFirstName(request.getFirstName());

        if (request.getLastName() != null)
            user.setLastName(request.getLastName());

        if (request.getMiddleName() != null)
            user.setMiddleName(request.getMiddleName());

        if (request.getPassword() != null)
            user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }


    public void deleteUser(Long id, String header) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found"); // TODO Do custom exceptions
        }

        String token = header.substring(7);

        User user = optionalUser.get();

        String tokenEmail = jwtService.extractUsername(token);

        if (user.getEmail().equals(tokenEmail)) {
            throw new RuntimeException("You can't delete yourself");
        }

        String userRole = user.getRole().name();
        String tokenRole = jwtService.extractRole(token);
        if (
                userRole.equals(Role.ADMIN) && tokenRole.equals(Role.MANAGER) ||
                        userRole.equals(Role.MANAGER) && tokenRole.equals(Role.MANAGER)
        ) {
            throw new RuntimeException("You can't delete " + userRole);
        }

        userRepository.deleteById(id);
    }
}
