package com.aromero.theamcrmservice.auth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    public void correctLoginTest() {
        //given
        LoginRequest loginRequest = new LoginRequest("user1@domain.com", "test");

        //when
        LoginResponse loginResponse = authService.login(loginRequest);

        //then
        Assert.assertEquals(1L, loginResponse.getUser().getId().longValue());
        Assert.assertTrue(!loginResponse.getToken().isEmpty());
    }

    @Test
    public void invalidCredentialLoginTest() {
        try {
            //given
            LoginRequest loginRequest = new LoginRequest("invalid_user@domain.com", "wrong");

            //when
            authService.login(loginRequest);

            Assert.fail("A BadCredentialsException should be thrown");
        } catch (BadCredentialsException e) {
            //then
            Assert.assertTrue(true);
        }
    }
}
