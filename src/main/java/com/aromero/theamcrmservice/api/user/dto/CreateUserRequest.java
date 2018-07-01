package com.aromero.theamcrmservice.api.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "Information needed to create a new user")
public class CreateUserRequest {
    @ApiModelProperty(notes = "Name of the user", example = "John", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(notes = "Last name of the user", example = "Doe", required = true)
    @NotBlank
    private String lastName;

    @ApiModelProperty(notes = "Email of the user", example = "john.doe@example.com", required = true)
    @NotBlank
    private String email;

    @ApiModelProperty(notes = "Password of the user", example = "secretPassword", required = true)
    @NotBlank
    private String password;

    @ApiModelProperty(notes = "If the user is admin or not", example = "true", required = true)
    @NotBlank
    private boolean admin;

    public CreateUserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
