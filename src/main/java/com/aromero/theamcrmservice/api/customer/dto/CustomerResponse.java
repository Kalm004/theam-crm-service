package com.aromero.theamcrmservice.api.customer.dto;

import com.aromero.theamcrmservice.api.user.dto.UserForCustomerResponse;

public class CustomerResponse {
    private Long id;

    private String name;

    private String surname;

    private String photoTempUrl;

    private UserForCustomerResponse createdByUser;

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
