package com.aromero.theamcrmservice.auth;

import com.aromero.theamcrmservice.auth.dto.LoginRequest;
import com.aromero.theamcrmservice.auth.dto.LoginResponse;
import com.aromero.theamcrmservice.auth.mapper.CustomUserDetailsMapper;
import com.aromero.theamcrmservice.security.CustomUserDetails;
import com.aromero.theamcrmservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final CustomUserDetailsMapper customUserDetailsMapper;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider,
                       CustomUserDetailsMapper customUserDetailsMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.customUserDetailsMapper = customUserDetailsMapper;
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
                customUserDetailsMapper.mapFrom(customUserDetails),
                tokenProvider.generateToken(customUserDetails)
        );
    }

}
