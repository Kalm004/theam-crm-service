package com.aromero.theamcrmservice.user;

import com.aromero.theamcrmservice.user.dto.CreateUserRequest;
import com.aromero.theamcrmservice.user.dto.UpdateUserRequest;
import com.aromero.theamcrmservice.user.dto.UserResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api("Endpoints that will provide a CRUD for users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    @ApiOperation(value = "Get all not deleted users")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "User not authenticated"),
            @ApiResponse(code = 403, message = "User doesn't have the ADMIN role")
    })
    public List<UserResponse> getAll() {
        return userService.getAllUsers();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    @ApiOperation("Get an user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "User not authenticated"),
            @ApiResponse(code = 403, message = "User doesn't have the ADMIN role"),
            @ApiResponse(code = 404, message = "User with the specified id not found"),
            @ApiResponse(code = 410, message = "User with the specified id was deleted"),
    })
    public UserResponse getById(@PathVariable(value = "id") Long userId) {
        return userService.getUserResponseById(userId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public void createUser(@RequestBody CreateUserRequest user) {
        userService.createUser(user);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public void updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        updateUserRequest.setId(id);
        userService.updateUser(id, updateUserRequest);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ApiOperation("Mark the user as deleted, the user won't be part of the system unless it is created again")
    public void deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/admin")
    public void setUserAsAdmin(@PathVariable(value = "id") Long id) {
        userService.setUserAdminStatus(id, true);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}/admin")
    public void setUserAsNoAdmin(@PathVariable(value = "id") Long id) {
        userService.setUserAdminStatus(id, false);
    }
}
