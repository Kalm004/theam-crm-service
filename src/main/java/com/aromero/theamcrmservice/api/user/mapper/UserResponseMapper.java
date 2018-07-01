package com.aromero.theamcrmservice.api.user.mapper;

import com.aromero.theamcrmservice.api.user.User;
import com.aromero.theamcrmservice.api.user.dto.UserResponse;
import org.modelmapper.ModelMapper;

public class UserResponseMapper {
    private ModelMapper modelMapper;

    public UserResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponse mapToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
