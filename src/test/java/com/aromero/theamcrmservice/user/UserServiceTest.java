package com.aromero.theamcrmservice.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void getAllUsers() {
        List<User> allUsers = userService.getAllUsers();

        Assert.assertEquals(2, allUsers.size());
    }
    @Test
    public void getUserByIdFound() {
        Optional<User> user = userService.getUserById(1L);

        Assert.assertTrue(user.isPresent());
    }

    @Test
    public void getUserByNotFound() {
        Optional<User> user = userService.getUserById(100L);

        Assert.assertFalse(user.isPresent());
    }

    @Test
    @DirtiesContext
    public void addUser() {
        User user = new User();
        user.setName("Name2");
        user.setLastName("Lastname2");
        user.setEmail("name@mail.com");
        user.setHashedPassword("1234");
        user.setAdmin(false);

        userService.saveUser(user);

        List<User> userList = userService.getAllUsers();
        Assert.assertTrue(userList.stream().anyMatch(c -> c.getName().equals("Name2")));
    }

    @Test
    @DirtiesContext
    public void modifyUser() {
        Optional<User> user = userService.getUserById(1L);

        Assert.assertTrue(user.isPresent());

        user.get().setName("ModifiedName");

        userService.saveUser(user.get());

        Optional<User> modifiedUser = userService.getUserById(1L);

        Assert.assertTrue(modifiedUser.isPresent());
        Assert.assertEquals("ModifiedName", modifiedUser.get().getName());
    }

    @Test
    @DirtiesContext
    public void deleteUser() {
        userService.deleteUser(2L);

        List<User> userList = userService.getAllUsers();
        Assert.assertEquals(1, userList.size());
    }

    @Test
    @DirtiesContext
    public void setNoAdminUserAsAdmin() {
        userService.setUserAdminStatus(1L, true);

        Optional<User> user = userService.getUserById(1L);
        Assert.assertTrue(user.get().getAdmin());
    }

    @Test
    @DirtiesContext
    public void setAdminUserAsNoAdmin() {
        userService.setUserAdminStatus(2L, false);

        Optional<User> user = userService.getUserById(2L);
        Assert.assertFalse(user.get().getAdmin());
    }
}
