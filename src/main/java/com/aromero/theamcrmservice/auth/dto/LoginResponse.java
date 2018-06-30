package com.aromero.theamcrmservice.auth.dto;

import com.aromero.theamcrmservice.user.dto.UserResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Response of the login operation")
public class LoginResponse {
    @ApiModelProperty(notes = "Token that authenticate this user", example = "eyJhbGciOiJIUzU...", required = true)
    private String token;
    @ApiModelProperty(notes = "User information", required = true)
    private UserResponse user;

    public LoginResponse(UserResponse user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
