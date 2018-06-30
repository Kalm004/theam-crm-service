package com.aromero.theamcrmservice.integration;

import com.aromero.theamcrmservice.auth.dto.LoginRequest;
import com.aromero.theamcrmservice.auth.dto.LoginResponse;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserIntegrationTest {
    @LocalServerPort
    private Integer port;

    private String baseUrl;

    private LoginResponse adminLoginResponse;

    private LoginResponse noAdminLoginResponse;

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port;

        adminLoginResponse =
            given().
                contentType(ContentType.JSON).
                body(new LoginRequest("user2@domain.com", "test")).
            post(baseUrl + "/login").
                body().
                as(LoginResponse.class);

        noAdminLoginResponse =
            given().
                contentType(ContentType.JSON).
                body(new LoginRequest("user1@domain.com", "test")).
            post(baseUrl + "/login").
                body().
                as(LoginResponse.class);
    }

    @Test
    public void getAllUsers200AndGetListOfUsers() {
        given().
            header(new Header("Authorization", "Bearer " + adminLoginResponse.getToken())).
        when().
            get(baseUrl + "/users").
        then().
            statusCode(200).
            body("size()", equalTo(2));
    }

    @Test
    public void getAllUsers403WhenUserDontHaveAdminRole() {
        given().
            header(new Header("Authorization", "Bearer " + noAdminLoginResponse.getToken())).
            when().
        get(baseUrl + "/users").
            then().
            statusCode(403);
    }
}

