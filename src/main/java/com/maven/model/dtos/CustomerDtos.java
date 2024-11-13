package com.maven.model.dtos;

import lombok.Data;

@Data
public class CustomerDtos {
    private Long id;
    private String username;
    private String role;
    private Boolean enabled;
    private String contact;
    private String password;
    private byte[] image;
}
