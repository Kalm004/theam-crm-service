package com.aromero.theamcrmservice.api.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User information for customer request")
public class UserForCustomerResponse {
    @ApiModelProperty(notes = "Id of the user", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Email of the user", example = "john.doe@example.com", required = true)
    private String email;

    public UserForCustomerResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
