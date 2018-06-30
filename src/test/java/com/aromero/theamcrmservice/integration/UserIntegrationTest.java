package com.aromero.theamcrmservice.integration;

import com.aromero.theamcrmservice.auth.dto.LoginRequest;
import com.aromero.theamcrmservice.auth.dto.LoginResponse;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
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
        getWithUserAndExpectedCodeResult(adminLoginResponse.getToken(), "/users", 200).
            body("size()", equalTo(2));
    }

    @Test
    public void getUser200WhenUserIsAdmin() {
        getWithUserAndExpectedCodeResult(adminLoginResponse.getToken(), "/users/1", 200).
            body("name", equalTo("User1"));
    }

    @Test
    public void getUser404WhenUserDoesntExist() {
        getWithUserAndExpectedCodeResult(adminLoginResponse.getToken(), "/users/100", 404);
    }

    @Test
    public void getUser410WhenUserIsDeleted() {
        getWithUserAndExpectedCodeResult(adminLoginResponse.getToken(), "/users/3", 410);
    }

    @Test
    @DirtiesContext
    public void deleteUser200WhenUserIsAdmin() {
        deleteWithUserAndExceptedStatusCode(adminLoginResponse.getToken(), "/users/2", 200);
    }

    @Test
    public void deleteUser410WhenUserIsAlreadyReleased() {
        deleteWithUserAndExceptedStatusCode(adminLoginResponse.getToken(), "/users/3", 410);
    }

    @Test
    public void deleteUser404WhenUserNeverExisted() {
        deleteWithUserAndExceptedStatusCode(adminLoginResponse.getToken(), "/users/100", 404);
    }

    @Test
    public void error401WhenNotAuthenticatedForAllEndpoints() {
        getWithUserAndExpectedCodeResult("", "/users", 401);

        getWithUserAndExpectedCodeResult("", "/users/1", 401);

        deleteWithUserAndExceptedStatusCode("", "/users/1", 401);
    }

    @Test
    public void error403WhenUserDontHaveAdminRoleAllEndpoints() {
        getWithUserAndExpectedCodeResult(noAdminLoginResponse.getToken(), "/users", 403);

        getWithUserAndExpectedCodeResult(noAdminLoginResponse.getToken(), "/users/1", 403);

        deleteWithUserAndExceptedStatusCode(noAdminLoginResponse.getToken(), "/users/1", 403);
    }

    private ValidatableResponse getWithUserAndExpectedCodeResult(
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

    private void deleteWithUserAndExceptedStatusCode(
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
}

