package com.fatia.Auth.user.controllers;

import com.fatia.Auth.user.entities.UserModel;
import com.fatia.Auth.user.requests.RegisterUserRequest;
import com.fatia.Auth.user.requests.UpdateUserRequest;
import com.fatia.Auth.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register-manager")
    public ResponseEntity registerManager(
            @RequestBody RegisterUserRequest request
    ) {
        userService.registerManager(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register-worker")
    public ResponseEntity registerWorker(
            @RequestBody RegisterUserRequest request
    ) {
        userService.registerWorker(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-user/{email}")
    public ResponseEntity updateUser(
            @RequestBody UpdateUserRequest request,
            @PathVariable String email
    ) {
        userService.updateUser(request, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity deleteUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String header
    ) {
        try {
            userService.deleteUser(id, header);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
