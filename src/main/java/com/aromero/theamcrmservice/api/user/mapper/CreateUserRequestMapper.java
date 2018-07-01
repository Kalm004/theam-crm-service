package com.aromero.theamcrmservice.api.user.mapper;

import com.aromero.theamcrmservice.api.user.User;
import com.aromero.theamcrmservice.api.user.dto.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateUserRequestMapper {
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreateUserRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User mapToUser(CreateUserRequest createUserRequest) {
        User user = modelMapper.map(createUserRequest, User.class);

        user.setHashedPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        user.setDeleted(false);

        return user;
    }
}
