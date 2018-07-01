package com.aromero.theamcrmservice.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "Information needed to update a user")
public class UpdateUserRequest {
    @ApiModelProperty(notes = "Name of the user", example = "John", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(notes = "Last name of the user", example = "Doe", required = true)
    @NotBlank
    private String lastName;

    @ApiModelProperty(notes = "Password of the user", example = "secretPassword")
    private String password;

    @ApiModelProperty(notes = "If the user is admin or not", example = "true", required = true)
    @NotBlank
    private boolean admin;

    public UpdateUserRequest() {
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
