package com.aromero.theamcrmservice.user;

import com.aromero.theamcrmservice.user.dto.CreateUserRequest;
import com.aromero.theamcrmservice.user.dto.UpdateUserRequest;
import com.aromero.theamcrmservice.user.dto.UserResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(description = "Controller that provides a CRUD for users")
@ApiResponses(value = {
        @ApiResponse(code = 401, message = "User not authenticated"),
        @ApiResponse(code = 403, message = "User doesn't have the ADMIN role")
})
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    @ApiOperation(value = "Get all not deleted users")
    public List<UserResponse> getAll() {
        return userService.getAllUsers();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    @ApiOperation("Get an user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User with the specified id not found"),
            @ApiResponse(code = 410, message = "User with the specified id was deleted")
    })
    public UserResponse getById(@ApiParam("Id of the user") @PathVariable(value = "id") Long userId) {
        return userService.getUserResponseById(userId);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ApiOperation("Mark the user as deleted, the user won't be part of the system unless it is created again")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User with the specified id not found"),
            @ApiResponse(code = 410, message = "User with the specified id has already been deleted"),
    })
    public void deleteUser(@ApiParam("Id of the user to be deleted") @PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @ApiOperation("Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Body is not a valid CreateUserRequest object"),
            @ApiResponse(code = 409, message = "User with the specified e-mail already exists")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@ApiParam("Data needed to create the user") @Valid @RequestBody CreateUserRequest user) {
        return userService.createUser(user);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    @ApiOperation("Updates a user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Body is not a valid UpdateUserRequest object"),
            @ApiResponse(code = 404, message = "User with the specified id not found"),
            @ApiResponse(code = 410, message = "User with the specified id was deleted")
    })
    public void updateUser(@ApiParam("Id of the user to be deleted") @PathVariable(value = "id") Long id,
                           @ApiParam("Data needed to update the user") @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUser(id, updateUserRequest);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/admin")
    @ApiOperation("Add the admin status to the user")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User with the specified id not found"),
            @ApiResponse(code = 410, message = "User with1º the specified id was deleted")
    })
    public void setUserAsAdmin(@ApiParam("Id of the user to be deleted") @PathVariable(value = "id") Long id) {
        userService.setUserAdminStatus(id, true);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}/admin")
    @ApiOperation("Remove the admin status of the user")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User with the specified id not found"),
            @ApiResponse(code = 410, message = "User with the specified id was deleted")
    })
    public void setUserAsNoAdmin(@ApiParam("Id of the user to be deleted") @PathVariable(value = "id") Long id) {
        userService.setUserAdminStatus(id, false);
    }
}
