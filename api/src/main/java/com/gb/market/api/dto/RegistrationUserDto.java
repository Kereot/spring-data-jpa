package com.gb.market.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUserDto {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
