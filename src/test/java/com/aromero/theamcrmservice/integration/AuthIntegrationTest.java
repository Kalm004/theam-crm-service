package com.aromero.theamcrmservice.integration;

import com.aromero.theamcrmservice.api.auth.dto.LoginRequest;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {
    @LocalServerPort
    private Integer port;

    private String baseUrl;

    @Before
    public void before() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void correctLogin200AndLoginResponse() {
        given().
            contentType(ContentType.JSON).
            body(new LoginRequest("user1@domain.com", "test")).
        when().
            post(baseUrl + "/login").
        then().
            statusCode(200).
            body("token", notNullValue()).
            body("user.id", equalTo(1));
    }

    @Test
    public void badCredentialsLogin401() {
        given().
            contentType(ContentType.JSON).
            body(new LoginRequest("invalid_user@domain.com", "wrong")).
        when().
            post(baseUrl + "/login").
        then().
            statusCode(401);
    }

    @Test
    public void emptyEmailLogin400() {
        given().
            contentType(ContentType.JSON).
            body(new LoginRequest("", "wrong")).
        when().
            post(baseUrl + "/login").
        then().
            statusCode(400).
            body("status", equalTo("BAD_REQUEST"));
    }
}
