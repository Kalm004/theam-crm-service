package com.aromero.theamcrmservice.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "Information needed for the login process")
public class LoginRequest {
    @ApiModelProperty(notes = "Email of the user", required = true, example = "john.doe@example.com")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @ApiModelProperty(notes = "Password of the user", required = true, example = "secretPassword")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
