package com.aromero.theamcrmservice.user.mapper;

import com.aromero.theamcrmservice.mapper.Mapper;
import com.aromero.theamcrmservice.user.User;
import com.aromero.theamcrmservice.user.dto.UserResponse;
import org.modelmapper.ModelMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UserResponseMapper implements Mapper<User, UserResponse> {
    private ModelMapper modelMapper;

    public UserResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse mapTo(User source) {
        return modelMapper.map(source, UserResponse.class);
    }

    @Override
    public User mapFrom(UserResponse target) {
        throw new NotImplementedException();
    }
}
