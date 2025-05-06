package com.fatia.Auth.UserService.entities;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserModel {
    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private Role role;

    public static UserModel toModel(User user) {
        var userModel = UserModel.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();

        return userModel;
    }
}
