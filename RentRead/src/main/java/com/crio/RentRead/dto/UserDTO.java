package com.crio.RentRead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role; // Default role will be set in service if not provided
}
