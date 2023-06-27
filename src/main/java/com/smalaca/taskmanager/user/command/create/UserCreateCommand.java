package com.smalaca.taskmanager.user.command.create;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskmanager.user.command.UserCommandRepository;

import java.util.Optional;

public class UserCreateCommand {
    private final UserCommandRepository userRepository;
    private final UserFactory userFactory;

    public UserCreateCommand(UserCommandRepository userRepository) {
        this.userRepository = userRepository;
        this.userFactory = new UserFactory();
    }

    public Optional<Long> createUser(UserCreateCommandInput input) {
        if (userRepository.existsByFirstNameAndLastName(input.getFirstName(), input.getLastName())) {
            return Optional.empty();
        } else {
            User user = userFactory.create(input);
            Long savedId = userRepository.save(user);
            return Optional.of(savedId);
        }
    }

}
