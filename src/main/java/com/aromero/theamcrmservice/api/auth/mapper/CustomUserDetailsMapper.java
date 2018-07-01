package com.aromero.theamcrmservice.api.auth.mapper;

import com.aromero.theamcrmservice.api.user.dto.UserResponse;
import com.aromero.theamcrmservice.security.CustomUserDetails;
import org.modelmapper.ModelMapper;

public class CustomUserDetailsMapper {
    private ModelMapper modelMapper;

    public CustomUserDetailsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponse mapToUserResponse(CustomUserDetails customUserDetails) {
        return modelMapper.map(customUserDetails, UserResponse.class);
    }
}
