package com.fatia.Auth.user.requests;


import com.fatia.Auth.user.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private Role role;
}
