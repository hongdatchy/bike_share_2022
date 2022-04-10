package com.hongdatchy.bikeshare.entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class LoginResponse {
    private String role;
    private String token;
}
