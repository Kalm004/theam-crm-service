package com.aromero.theamcrmservice.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public void createUser(@RequestBody UserCreationDTO user) {
        userService.saveUser(convertToEntity(user));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public void updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {
        userService.saveUser(user);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
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

    private User convertToEntity(UserCreationDTO userCreationDTO) {
        User user = modelMapper().map(userCreationDTO, User.class);

        user.setHashedPassword(passwordEncoder.encode(userCreationDTO.getPassword()));

        //TODO: check if the user already exist in the database

        return user;
    }
}
