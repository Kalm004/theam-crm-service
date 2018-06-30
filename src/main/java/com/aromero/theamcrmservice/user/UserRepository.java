package com.aromero.theamcrmservice.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailAndDeletedFalse(String email);
    Optional<User> findByEmail(String email);
    List<User> findByDeletedFalse();
}
