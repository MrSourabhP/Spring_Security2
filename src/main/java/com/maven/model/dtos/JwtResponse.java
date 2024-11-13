package com.maven.model.dtos;

import lombok.Data;

@Data
public class JwtResponse {
    private String username;
    private String role;
    private String token;
}
