package com.aromero.theamcrmservice.api.user;

import com.aromero.theamcrmservice.exception.EntityGoneException;
import com.aromero.theamcrmservice.api.user.dto.CreateUserRequest;
import com.aromero.theamcrmservice.api.user.dto.UpdateUserRequest;
import com.aromero.theamcrmservice.api.user.dto.UserResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    private static final int NUMBER_OF_NOT_DELETED_USERS = 3;

    @Autowired
    private UserService userService;

    //TODO: test that deleted users are not returned by the read endpoints

    @Test
    public void getAllUsers() {
        List<UserResponse> allUsers = userService.getAllUsers();

        Assert.assertEquals(NUMBER_OF_NOT_DELETED_USERS, allUsers.size());
    }
    @Test
    public void getUserByIdFound() {
        UserResponse userResponse = userService.getUserResponseById(1L);

        Assert.assertTrue(true);
        Assert.assertNotNull(userResponse);
    }

    @Test
    public void getUserByIdNotFound() {
        try {
            userService.getUserResponseById(100L);
            Assert.fail("An EntityNotFoundException should have been thrown");
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getDeletedUserById() {
        try {
            userService.getUserResponseById(3L);
            Assert.fail("An EntityGoneException should have been thrown");
        } catch (EntityGoneException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void createNewUser() {
        CreateUserRequest userToCreate = new CreateUserRequest();
        userToCreate.setName("Name2");
        userToCreate.setLastName("Lastname2");
        userToCreate.setEmail("name@mail.com");
        userToCreate.setPassword("test");
        userToCreate.setAdmin(false);

        UserResponse userResponse = userService.createUser(userToCreate);

        Assert.assertEquals("Name2", userResponse.getName());
    }

    @Test
    public void tryToCreateUserWithExistingEmail() {
        CreateUserRequest userToCreate = new CreateUserRequest();
        userToCreate.setName("NewUser");
        userToCreate.setLastName("Lastname");
        userToCreate.setEmail("user1@domain.com");
        userToCreate.setPassword("test");
        userToCreate.setAdmin(false);

        try {
            userService.createUser(userToCreate);
            Assert.fail("An EntityExistsException should have been thrown");
        } catch (EntityExistsException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void createDeletedUserKeepSameId() {
        CreateUserRequest userToCreate = new CreateUserRequest();
        userToCreate.setName("NewUser");
        userToCreate.setLastName("Lastname");
        userToCreate.setEmail("deleted@domain.com");
        userToCreate.setPassword("test");
        userToCreate.setAdmin(false);

        UserResponse userResponse = userService.createUser(userToCreate);
        Assert.assertEquals((Long)3L, userResponse.getId());
    }

    @Test
    @DirtiesContext
    public void modifyUser() {
        UserResponse userResponse = userService.getUserResponseById(1L);

        userResponse.setName("ModifiedName");

        UpdateUserRequest updateUserRequest = new ModelMapper().map(userResponse, UpdateUserRequest.class);
        userService.updateUser(1L, updateUserRequest);

        UserResponse modifiedUserResponse = userService.getUserResponseById(1L);

        Assert.assertNotNull(modifiedUserResponse);
        Assert.assertEquals("ModifiedName", modifiedUserResponse.getName());
    }

    @Test
    @DirtiesContext
    public void tryToModifyNotExistingUser() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setAdmin(false);
        updateUserRequest.setName("Test");
        updateUserRequest.setLastName("LastTest");
        try {
            userService.updateUser(100L, updateUserRequest);
            Assert.fail("An EntityNotFoundException should have been thrown");
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void tryToModifyDeletedUser() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setAdmin(false);
        updateUserRequest.setName("Test");
        updateUserRequest.setLastName("LastTest");
        try {
            userService.updateUser(3L, updateUserRequest);
            Assert.fail("An EntityGoneException should have been thrown");
        } catch (EntityGoneException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void deleteExistingUser() {
        userService.deleteUser(2L);

        List<UserResponse> userList = userService.getAllUsers();
        Assert.assertEquals(NUMBER_OF_NOT_DELETED_USERS - 1, userList.size());
    }

    @Test
    @DirtiesContext
    public void deleteAlreadyDeletedUser() {
        try {
            userService.deleteUser(3L);
            Assert.fail("An EntityGoneException should have been thrown");
        } catch (EntityGoneException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void deleteUserNotFound() {
        try {
            userService.deleteUser(10L);
            Assert.fail("An EntityNotFoundException should have been thrown");
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void setNoAdminUserAsAdmin() {
        userService.setUserAdminStatus(1L, true);

        UserResponse userResponse = userService.getUserResponseById(1L);
        Assert.assertTrue(userResponse.getAdmin());
    }

    @Test
    @DirtiesContext
    public void setAdminUserAsNotAdmin() {
        userService.setUserAdminStatus(2L, false);

        UserResponse userResponse = userService.getUserResponseById(2L);
        Assert.assertFalse(userResponse.getAdmin());
    }

    @Test
    @DirtiesContext
    public void tryToSetNotExistingUserAsAdmin() {
        try {
            userService.setUserAdminStatus(100L, true);
            Assert.fail("An EntityNotFoundException should have been thrown");
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void tryToSetNotExistingUserAsNotAdmin() {
        try {
            userService.setUserAdminStatus(100L, false);
            Assert.fail("An EntityNotFoundException should have been thrown");
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void tryToSetDeletedUserAsAdmin() {
        try {
            userService.setUserAdminStatus(3L, true);
            Assert.fail("An EntityGoneException should have been thrown");
        } catch (EntityGoneException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void tryToSetDeletedUserAsNotAdmin() {
        try {
            userService.setUserAdminStatus(3L, false);
            Assert.fail("An EntityGoneException should have been thrown");
        } catch (EntityGoneException e) {
            Assert.assertTrue(true);
        }
    }
}
