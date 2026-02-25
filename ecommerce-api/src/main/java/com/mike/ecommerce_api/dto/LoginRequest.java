package com.mike.ecommerce_api.dto;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
