package com.aromero.theamcrmservice.integration;

import com.aromero.theamcrmservice.api.user.dto.CreateUserRequest;
import com.aromero.theamcrmservice.api.user.dto.UpdateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserIntegrationTest extends BaseIntegrationTest {
    private static final int NUMBER_OF_NOT_DELETED_USERS = 3;

    @Test
    public void getAllUsers200AndGetListOfUsers() {
        getWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users", 200).
            body("size()", equalTo(NUMBER_OF_NOT_DELETED_USERS));
    }

    @Test
    public void getUser200WhenUserIsAdmin() {
        getWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/1", 200).
            body("name", equalTo("User1"));
    }

    @Test
    public void getUser404WhenUserDoesntExist() {
        getWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/100", 404);
    }

    @Test
    public void getUser410WhenUserIsDeleted() {
        getWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/3", 410);
    }

    @Test
    @DirtiesContext
    public void deleteUser200WhenUserIsAdmin() {
        deleteWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/2", 200);
    }

    @Test
    @DirtiesContext
    public void deleteUser410WhenUserIsAlreadyReleased() {
        deleteWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/3", 410);
    }

    @Test
    @DirtiesContext
    public void deleteUser404WhenUserNeverExisted() {
        deleteWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/100", 404);
    }

    @Test
    @DirtiesContext
    public void createUser201WhenUserIsAdmin() {
        postWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users", 201,
                new CreateUserRequest("Test", "Test", "test@test.com", "test", false));
    }

    @Test
    @DirtiesContext
    public void createUser400WhenUserDataIsNotValid() {
        postWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users", 400,
                new CreateUserRequest("Test", "", "test@test.com", "test", false));
    }

    @Test
    @DirtiesContext
    public void createUser409WhenEmailAlreadyExists() {
        postWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users", 409,
                new CreateUserRequest("Test", "test", "user1@domain.com", "test", false));
    }

    @Test
    @DirtiesContext
    public void updateUser200WhenUserIsAdmin() {
        putWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/1", 200,
                new UpdateUserRequest("Test", "Test", "test", false));
    }

    @Test
    @DirtiesContext
    public void updateUser410WhenDeletedUser() {
        putWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/3", 410,
                new UpdateUserRequest("Test", "Test", "test", false));
    }

    @Test
    @DirtiesContext
    public void updateUser404WhenUserNotFound() {
        putWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/100", 404,
                new UpdateUserRequest("Test", "Test", "test", false));
    }

    @Test
    @DirtiesContext
    public void updateUser400WhenDataNotComplete() {
        putWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/1", 400,
                new UpdateUserRequest("", "Test", "test", false));
    }

    @Test
    @DirtiesContext
    public void setAdminAsUser200WhenUserIsAdmin() {
        postWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/1/admin", 200, null);
    }

    @Test
    @DirtiesContext
    public void setAdminToUser404WhenUserNotExists() {
        postWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/100/admin", 404, null);
    }

    @Test
    @DirtiesContext
    public void setAdminToUser410WhenUserHasBeenDeleted() {
        postWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/3/admin", 410, null);
    }

    @Test
    @DirtiesContext
    public void removeAdminAsUser200WhenUserIsAdmin() {
        deleteWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/4/admin", 200);
    }

    @Test
    @DirtiesContext
    public void removeAdminToUser404WhenUserNotExists() {
        deleteWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/100/admin", 404);
    }

    @Test
    @DirtiesContext
    public void removeAdminToUser410WhenUserHasBeenDeleted() {
        deleteWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/3/admin", 410);
    }

    @Test
    @DirtiesContext
    public void removeAdminToUser400WhenTheUserIsTheLoginUser() {
        deleteWithTokenAndExpectedStatusCode(adminLoginResponse.getToken(), "/users/2/admin", 400);
    }

    @Test
    public void error401WhenNotAuthenticatedForAllEndpoints() {
        getWithTokenAndExpectedStatusCode("", "/users", 401);

        getWithTokenAndExpectedStatusCode("", "/users/1", 401);

        deleteWithTokenAndExpectedStatusCode("", "/users/1", 401);

        postWithTokenAndExpectedStatusCode("", "/users", 401, new CreateUserRequest());

        putWithTokenAndExpectedStatusCode("", "/users/1", 401, new UpdateUserRequest());

        postWithTokenAndExpectedStatusCode("", "/users/1/admin", 401, null);

        deleteWithTokenAndExpectedStatusCode("", "/users/1/admin", 401);
    }

    @Test
    public void error403WhenUserDontHaveAdminRoleAllEndpoints() {
        getWithTokenAndExpectedStatusCode(noAdminLoginResponse.getToken(), "/users", 403);

        getWithTokenAndExpectedStatusCode(noAdminLoginResponse.getToken(), "/users/1", 403);

        deleteWithTokenAndExpectedStatusCode(noAdminLoginResponse.getToken(), "/users/1", 403);

        postWithTokenAndExpectedStatusCode(noAdminLoginResponse.getToken(), "/users", 403,
                new CreateUserRequest("Test", "Test", "test@test.com", "test", false));

        putWithTokenAndExpectedStatusCode(noAdminLoginResponse.getToken(), "/users/1", 403,
                new UpdateUserRequest("test", "test", "test", false));

        postWithTokenAndExpectedStatusCode(noAdminLoginResponse.getToken(), "/users/1/admin", 403, null);

        deleteWithTokenAndExpectedStatusCode(noAdminLoginResponse.getToken(), "/users/1/admin", 403);
    }

}

