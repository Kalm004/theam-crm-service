package com.aromero.theamcrmservice.auth;

import com.aromero.theamcrmservice.security.CustomUserDetails;
import com.aromero.theamcrmservice.security.JwtTokenProvider;
import com.aromero.theamcrmservice.user.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        return new LoginResponse(
                convertUserDetailsToUserResponse(customUserDetails),
                tokenProvider.generateToken(customUserDetails)
        );
    }

    private UserResponse convertUserDetailsToUserResponse(CustomUserDetails customUserDetails) {
        return modelMapper().map(customUserDetails, UserResponse.class);
    }
}
