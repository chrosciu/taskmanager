package com.smalaca.taskmanager.user.command;

import com.smalaca.taskamanager.model.entities.User;

import java.util.Optional;

public interface UserCommandRepository {
    Optional<User> findByUserNameFirstNameAndUserNameLastName(String firstName, String lastName);
    User save(User user);
    Optional<User> findById(Long id);
}
