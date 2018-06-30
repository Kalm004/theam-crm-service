package com.aromero.theamcrmservice.auth.mapper;

import com.aromero.theamcrmservice.mapper.Mapper;
import com.aromero.theamcrmservice.security.CustomUserDetails;
import com.aromero.theamcrmservice.user.dto.UserResponse;
import org.modelmapper.ModelMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CustomUserDetailsMapper implements Mapper<UserResponse, CustomUserDetails> {
    private ModelMapper modelMapper;

    public CustomUserDetailsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomUserDetails mapTo(UserResponse userResponse) {
        throw new NotImplementedException();
    }

    @Override
    public UserResponse mapFrom(CustomUserDetails customUserDetails) {
        return modelMapper.map(customUserDetails, UserResponse.class);
    }
}
