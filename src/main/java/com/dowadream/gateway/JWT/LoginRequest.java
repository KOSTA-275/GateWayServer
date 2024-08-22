package com.dowadream.gateway.JWT;
import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
    private String role;

}
