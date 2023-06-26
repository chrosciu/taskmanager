package com.smalaca.anticorruptionlayer;

import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.UserRepository;
import com.smalaca.taskmanager.user.command.UserCommandRepository;

import java.util.Optional;

public class UserAntiCorruptionLayer implements UserCommandRepository {
    private final UserRepository userRepository;

    public UserAntiCorruptionLayer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByUserNameFirstNameAndUserNameLastName(firstName, lastName).isPresent();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
