package com.aromero.theamcrmservice.api.auth;

import com.aromero.theamcrmservice.api.auth.dto.LoginRequest;
import com.aromero.theamcrmservice.api.auth.dto.LoginResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(description = "Controller that provides a login endpoint.")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ApiOperation("Login endpoint that will return a token if the user is correctly authenticated")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Empty email or password"),
            @ApiResponse(code = 401, message = "Bad credentials")
    })
    public LoginResponse login(@ApiParam("Email and password of the user") @Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
