package com.smalaca.taskmanager.user.command;

import com.smalaca.taskamanager.model.entities.User;

import java.util.Optional;

public interface UserCommandRepository {
    boolean existsByUserName(String firstName, String lastName);

    Optional<User> findById(Long id);

    Long save(User user);
}
