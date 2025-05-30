package com.fatia.Auth.user.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
}
