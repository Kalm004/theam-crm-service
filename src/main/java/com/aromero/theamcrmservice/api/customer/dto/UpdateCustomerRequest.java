package com.aromero.theamcrmservice.api.customer.dto;

import javax.validation.constraints.NotBlank;

public class UpdateCustomerRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    public UpdateCustomerRequest() {
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
}
