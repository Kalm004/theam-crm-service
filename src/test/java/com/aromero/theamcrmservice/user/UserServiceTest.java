package com.aromero.theamcrmservice.user;

import com.aromero.theamcrmservice.user.dto.CreateUserRequest;
import com.aromero.theamcrmservice.user.dto.UpdateUserRequest;
import com.aromero.theamcrmservice.user.dto.UserResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    private static final int NUMBER_OF_NOT_DELETED_USERS = 2;

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
    public void getUserByNotFound() {
        try {
            userService.getUserResponseById(100L);
            Assert.fail("An EntityNotFoundException should have been thrown");
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    public void addUser() {
        CreateUserRequest user = new CreateUserRequest();
        user.setName("Name2");
        user.setLastName("Lastname2");
        user.setEmail("name@mail.com");
        user.setPassword("test");
        user.setAdmin(false);

        userService.createUser(user);

        List<UserResponse> userList = userService.getAllUsers();
        Assert.assertTrue(userList.stream().anyMatch(c -> c.getName().equals("Name2")));
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
    public void deleteUser() {
        userService.deleteUser(2L);

        List<UserResponse> userList = userService.getAllUsers();
        Assert.assertEquals(NUMBER_OF_NOT_DELETED_USERS - 1, userList.size());
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
    public void setAdminUserAsNoAdmin() {
        userService.setUserAdminStatus(2L, false);

        UserResponse userResponse = userService.getUserResponseById(2L);
        Assert.assertFalse(userResponse.getAdmin());
    }
}
