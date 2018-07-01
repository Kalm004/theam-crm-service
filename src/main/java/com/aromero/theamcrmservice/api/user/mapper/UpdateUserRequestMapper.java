package com.aromero.theamcrmservice.api.user.mapper;

import com.aromero.theamcrmservice.mapper.Mapper;
import com.aromero.theamcrmservice.api.user.User;
import com.aromero.theamcrmservice.api.user.dto.UpdateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UpdateUserRequestMapper implements Mapper<User, UpdateUserRequest> {
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UpdateUserRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UpdateUserRequest mapTo(User user) {
        throw new NotImplementedException();
    }

    @Override
    public User mapFrom(UpdateUserRequest updateUserRequest) {
        User user = modelMapper.map(updateUserRequest, User.class);

        if (updateUserRequest.getPassword() != null) {
            user.setHashedPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        user.setDeleted(false);

        return user;
    }
}
