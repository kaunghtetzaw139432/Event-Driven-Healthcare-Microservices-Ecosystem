package com.pm.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be a valid email address")
    private  String email;

    @NotBlank(message = "Password is required")
    @Size(message = "Password must be at least 8 characters long",min=8)
    private  String password;
}
