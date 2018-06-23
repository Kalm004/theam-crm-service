package com.aromero.theamcrmservice.customer;

import javax.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "customer_Sequence")
    @SequenceGenerator(name = "customer_Sequence", sequenceName = "CUSTOMER_SEQ")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;
    //TODO: add a photo field and a reference to the user

    public Customer() {
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
}
