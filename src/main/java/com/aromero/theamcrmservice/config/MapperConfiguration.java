package com.aromero.theamcrmservice.config;

import com.aromero.theamcrmservice.api.auth.mapper.CustomUserDetailsMapper;
import com.aromero.theamcrmservice.api.user.mapper.CreateUserRequestMapper;
import com.aromero.theamcrmservice.api.user.mapper.UpdateUserRequestMapper;
import com.aromero.theamcrmservice.api.user.mapper.UserResponseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    private ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserResponseMapper userResponseMapper() {
        return new UserResponseMapper(modelMapper());
    }

    @Bean
    public CreateUserRequestMapper createUserRequestMapper() {
        return new CreateUserRequestMapper(modelMapper());
    }

    @Bean
    public UpdateUserRequestMapper updateUserRequestMapper() {
        return new UpdateUserRequestMapper(modelMapper());
    }

    @Bean
    public CustomUserDetailsMapper customUserDetailsMapper() {
        return new CustomUserDetailsMapper(modelMapper());
    }
}
