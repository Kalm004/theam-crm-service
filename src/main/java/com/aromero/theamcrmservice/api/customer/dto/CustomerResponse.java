package com.aromero.theamcrmservice.api.customer.dto;

import com.aromero.theamcrmservice.api.user.dto.UserResponse;

public class CustomerResponse {
    private Long id;

    private String name;

    private String surname;

    private String photoUrl;

    private UserResponse createdByUser;

    private UserResponse modifiedByUser;

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public UserResponse getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(UserResponse createdByUser) {
        this.createdByUser = createdByUser;
    }

    public UserResponse getModifiedByUser() {
        return modifiedByUser;
    }

    public void setModifiedByUser(UserResponse modifiedByUser) {
        this.modifiedByUser = modifiedByUser;
    }
}
