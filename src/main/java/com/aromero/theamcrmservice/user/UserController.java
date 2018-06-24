package com.aromero.theamcrmservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @PostMapping("/user")
    public void createUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PutMapping("/user/{id}")
    public void updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {
        userService.saveUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("user/{id}/admin")
    public void setUserAsAdmin(@PathVariable(value = "id") Long id) {
        userService.setUserAdminStatus(id, true);
    }

    @DeleteMapping("user/{id}/admin")
    public void setUserAsNoAdmin(@PathVariable(value = "id") Long id) {
        userService.setUserAdminStatus(id, false);
    }
}
