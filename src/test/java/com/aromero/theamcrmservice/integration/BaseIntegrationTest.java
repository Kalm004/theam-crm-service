package com.aromero.theamcrmservice.integration;

import com.aromero.theamcrmservice.api.auth.dto.LoginRequest;
import com.aromero.theamcrmservice.api.auth.dto.LoginResponse;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public abstract class BaseIntegrationTest {
    protected LoginResponse adminLoginResponse;
    protected LoginResponse noAdminLoginResponse;
    @LocalServerPort
    private Integer port;
    private String baseUrl;

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

    protected ValidatableResponse getWithTokenAndExpectedCodeResult(
            String token,
            String url,
            int expectedCode) {
        return given().
            header(new Header("Authorization", "Bearer " + token)).
        when().
            get(baseUrl + url).
        then().
            statusCode(expectedCode);
    }

    protected void deleteWithTokenAndExceptedStatusCode(
            String token,
            String url,
            int expectedStatusCode) {
        given().
            header(new Header("Authorization", "Bearer " + token)).
        when().
            delete(baseUrl + url).
        then().
            statusCode(expectedStatusCode);
    }

    protected void postWithTokenAndExceptedStatusCode(
            String token,
            String url,
            int expectedStatusCode,
            Object object) {
        RequestSpecification requestSpecification = given().
            header(new Header("Authorization", "Bearer " + token));

        if (object != null) {
            requestSpecification = requestSpecification.
                    contentType(ContentType.JSON).
                    body(object);
        }

        requestSpecification.
        when().
            post(baseUrl + url).
        then().
            statusCode(expectedStatusCode);
    }

    protected void putWithTokenAndExceptedStatusCode(
            String token,
            String url,
            int expectedStatusCode,
            Object object) {
        RequestSpecification requestSpecification = given().
                header(new Header("Authorization", "Bearer " + token));

        if (object != null) {
            requestSpecification = requestSpecification.contentType(ContentType.JSON).
                    body(object);
        }

        requestSpecification.
        when().
            put(baseUrl + url).
        then().
            statusCode(expectedStatusCode);
    }
}
