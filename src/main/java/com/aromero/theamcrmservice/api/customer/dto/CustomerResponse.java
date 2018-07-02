package com.aromero.theamcrmservice.api.customer.dto;

import com.aromero.theamcrmservice.api.user.dto.UserForCustomerResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Customer information")
public class CustomerResponse {
    @ApiModelProperty(notes = "Id of the customer", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Name of the customer", example = "Jane", required = true)
    private String name;

    @ApiModelProperty(notes = "Surname of the customer", example = "Doe", required = true)
    private String surname;

    @ApiModelProperty(notes = "Temporarily link to the customer photo, it last 4 hours", example = "https://dropbox.com/example")
    private String photoTempUrl;

    @ApiModelProperty(notes = "User that created this customer", required = true)
    private UserForCustomerResponse createdByUser;

    @ApiModelProperty(notes = "User that has modified this customer")
    private UserForCustomerResponse modifiedByUser;

    public CustomerResponse() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhotoTempUrl() {
        return photoTempUrl;
    }

    public void setPhotoTempUrl(String photoTempUrl) {
        this.photoTempUrl = photoTempUrl;
    }

    public UserForCustomerResponse getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(UserForCustomerResponse createdByUser) {
        this.createdByUser = createdByUser;
    }

    public UserForCustomerResponse getModifiedByUser() {
        return modifiedByUser;
    }

    public void setModifiedByUser(UserForCustomerResponse modifiedByUser) {
        this.modifiedByUser = modifiedByUser;
    }
}
