package com.aromero.theamcrmservice.integration;

import com.aromero.theamcrmservice.api.auth.dto.LoginRequest;
import com.aromero.theamcrmservice.api.auth.dto.LoginResponse;
import com.aromero.theamcrmservice.api.user.dto.CreateUserRequest;
import com.aromero.theamcrmservice.api.user.dto.UpdateUserRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
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
        getWithTokenAndExpectedCodeResult(adminLoginResponse.getToken(), "/users", 200).
            body("size()", equalTo(2));
    }

    @Test
    public void getUser200WhenUserIsAdmin() {
        getWithTokenAndExpectedCodeResult(adminLoginResponse.getToken(), "/users/1", 200).
            body("name", equalTo("User1"));
    }

    @Test
    public void getUser404WhenUserDoesntExist() {
        getWithTokenAndExpectedCodeResult(adminLoginResponse.getToken(), "/users/100", 404);
    }

    @Test
    public void getUser410WhenUserIsDeleted() {
        getWithTokenAndExpectedCodeResult(adminLoginResponse.getToken(), "/users/3", 410);
    }

    @Test
    @DirtiesContext
    public void deleteUser200WhenUserIsAdmin() {
        deleteWithTokenAndExceptedStatusCode(adminLoginResponse.getToken(), "/users/2", 200);
    }

    @Test
    public void deleteUser410WhenUserIsAlreadyReleased() {
        deleteWithTokenAndExceptedStatusCode(adminLoginResponse.getToken(), "/users/3", 410);
    }

    @Test
    public void deleteUser404WhenUserNeverExisted() {
        deleteWithTokenAndExceptedStatusCode(adminLoginResponse.getToken(), "/users/100", 404);
    }

    @Test
    @DirtiesContext
    public void createUser201WhenUserIsAdmin() {
        postWithTokenAndExceptedStatusCode(adminLoginResponse.getToken(), "/users", 201,
                new CreateUserRequest("Test", "Test", "test@test.com", "test", false));
    }

    @Test
    @DirtiesContext
    public void createUser400WhenUserDataIsNotValid() {
        postWithTokenAndExceptedStatusCode(adminLoginResponse.getToken(), "/users", 400,
                new CreateUserRequest("Test", "", "test@test.com", "test", false));
    }

    @Test
    @DirtiesContext
    public void createUser409WhenEmailAlreadyExists() {
        postWithTokenAndExceptedStatusCode(adminLoginResponse.getToken(), "/users", 409,
                new CreateUserRequest("Test", "test", "user1@domain.com", "test", false));
    }

    @Test
    public void error401WhenNotAuthenticatedForAllEndpoints() {
        getWithTokenAndExpectedCodeResult("", "/users", 401);

        getWithTokenAndExpectedCodeResult("", "/users/1", 401);

        deleteWithTokenAndExceptedStatusCode("", "/users/1", 401);

        postWithTokenAndExceptedStatusCode("", "/users", 401, new CreateUserRequest());

        putWithTokenAndExceptedStatusCode("", "/users/1", 401, new UpdateUserRequest());

        postWithTokenAndExceptedStatusCode("", "/users/1/admin", 401, null);

        deleteWithTokenAndExceptedStatusCode("", "/users/1/admin", 401);
    }

    @Test
    public void error403WhenUserDontHaveAdminRoleAllEndpoints() {
        getWithTokenAndExpectedCodeResult(noAdminLoginResponse.getToken(), "/users", 403);

        getWithTokenAndExpectedCodeResult(noAdminLoginResponse.getToken(), "/users/1", 403);

        deleteWithTokenAndExceptedStatusCode(noAdminLoginResponse.getToken(), "/users/1", 403);

        postWithTokenAndExceptedStatusCode(noAdminLoginResponse.getToken(), "/users", 403,
                new CreateUserRequest("Test", "Test", "test@test.com", "test", false));

        putWithTokenAndExceptedStatusCode(noAdminLoginResponse.getToken(), "/users/1", 403,
                new UpdateUserRequest("test", "test", "test", false));

        postWithTokenAndExceptedStatusCode(noAdminLoginResponse.getToken(), "/users/1/admin", 403, null);

        deleteWithTokenAndExceptedStatusCode(noAdminLoginResponse.getToken(), "/users/1/admin", 403);
    }

    private ValidatableResponse getWithTokenAndExpectedCodeResult(
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

    private void deleteWithTokenAndExceptedStatusCode(
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

    private void postWithTokenAndExceptedStatusCode(
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

    private void putWithTokenAndExceptedStatusCode(
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

