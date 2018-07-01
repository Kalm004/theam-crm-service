package com.aromero.theamcrmservice.api.user.mapper;

import com.aromero.theamcrmservice.mapper.Mapper;
import com.aromero.theamcrmservice.api.user.User;
import com.aromero.theamcrmservice.api.user.dto.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CreateUserRequestMapper implements Mapper<User, CreateUserRequest> {
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreateUserRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CreateUserRequest mapTo(User user) {
        throw new NotImplementedException();
    }

    @Override
    public User mapFrom(CreateUserRequest createUserRequest) {
        User user = modelMapper.map(createUserRequest, User.class);

        user.setHashedPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        user.setDeleted(false);

        return user;
    }
}
