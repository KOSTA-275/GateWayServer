package com.dowadream.gateway.JWT;
import lombok.Data;

@Data
public class LoginRequest {
    private String userEmail;
    private String role;

}
