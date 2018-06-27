package com.aromero.theamcrmservice.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User information")
public class UserResponse {
    @ApiModelProperty(notes = "Id of the user", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Name of the user", example = "John", required = true)
    private String name;

    @ApiModelProperty(notes = "Last name of the user", example = "Doe", required = true)
    private String lastName;

    @ApiModelProperty(notes = "Email of the user", example = "john.doe@example.com", required = true)
    private String email;

    @ApiModelProperty(notes = "If the user is admin or not", example = "true", required = true)
    private Boolean admin;

    public UserResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
