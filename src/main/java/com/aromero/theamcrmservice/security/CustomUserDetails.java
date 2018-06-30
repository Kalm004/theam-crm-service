package com.aromero.theamcrmservice.security;

import com.aromero.theamcrmservice.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CustomUserDetails implements UserDetails {
    private Long id;

    private String name;

    private String lastName;

    private Boolean isAdmin;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String name, String lastName, Boolean isAdmin, String email, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new CustomUserDetails(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.isAdmin(),
                user.getEmail(),
                user.getHashedPassword(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //TODO: change to use the active field of the user
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CustomUserDetails otherCustomUserDetails = (CustomUserDetails) o;
        return Objects.equals(id, otherCustomUserDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
