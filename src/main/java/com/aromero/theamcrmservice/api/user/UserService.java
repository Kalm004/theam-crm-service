package com.aromero.theamcrmservice.api.user;

import com.aromero.theamcrmservice.exception.EntityGoneException;
import com.aromero.theamcrmservice.api.user.dto.CreateUserRequest;
import com.aromero.theamcrmservice.api.user.dto.UpdateUserRequest;
import com.aromero.theamcrmservice.api.user.dto.UserResponse;
import com.aromero.theamcrmservice.api.user.mapper.CreateUserRequestMapper;
import com.aromero.theamcrmservice.api.user.mapper.UpdateUserRequestMapper;
import com.aromero.theamcrmservice.api.user.mapper.UserResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserResponseMapper userResponseMapper;

    private final CreateUserRequestMapper createUserRequestMapper;

    private final UpdateUserRequestMapper updateUserRequestMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserResponseMapper userResponseMapper,
                       CreateUserRequestMapper createUserRequestMapper,
                       UpdateUserRequestMapper updateUserRequestMapper) {
        this.userRepository = userRepository;
        this.userResponseMapper = userResponseMapper;
        this.createUserRequestMapper = createUserRequestMapper;
        this.updateUserRequestMapper = updateUserRequestMapper;
    }

    @Transactional
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findByDeletedFalse();
        return convertUserListToUserResponseList(users);
    }

    @Transactional
    public UserResponse getUserResponseById(Long id) {
        return userResponseMapper.mapTo(getNotDeletedUserByIdOrThrow(id));
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getNotDeletedUserByIdOrThrow(id);

        user.setDeleted(true);

        userRepository.save(user);
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        Optional<User> userFromDatabase = userRepository.findByEmail(createUserRequest.getEmail());

        if (userFromDatabase.isPresent() && !userFromDatabase.get().isDeleted()) {
            throw new EntityExistsException("A user with the e-mail" + createUserRequest.getEmail() + " already exists");
        }
        User userToBeSaved = createUserRequestMapper.mapFrom(createUserRequest);
        userFromDatabase.ifPresent(user -> userToBeSaved.setId(user.getId()));

        userRepository.save(userToBeSaved);

        return userResponseMapper.mapTo(userToBeSaved);
    }

    @Transactional
    public void updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User userFromDatabase = getNotDeletedUserByIdOrThrow(userId);

        User userToBeSaved = updateUserRequestMapper.mapFrom(updateUserRequest);
        userToBeSaved.setId(userId);
        userToBeSaved.setEmail(userFromDatabase.getEmail());
        if (userToBeSaved.getHashedPassword() == null) {
            userToBeSaved.setHashedPassword(userFromDatabase.getHashedPassword());
        }

        userRepository.save(userToBeSaved);
    }

    @Transactional
    public void setUserAdminStatus(Long id, Boolean adminStatus) {
        User user = getNotDeletedUserByIdOrThrow(id);

        user.setAdmin(adminStatus);

        userRepository.save(user);
    }

    private User getNotDeletedUserByIdOrThrow(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.orElseThrow(EntityNotFoundException::new).isDeleted()) {
            throw new EntityGoneException("User with id " + id + " has been deleted");
        }

        return user.get();
    }

    private List<UserResponse> convertUserListToUserResponseList(List<User> users) {
        return users
                .stream()
                .map(userResponseMapper::mapTo)
                .collect(Collectors.toList());
    }

}
