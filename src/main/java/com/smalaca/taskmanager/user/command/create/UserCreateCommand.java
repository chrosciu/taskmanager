package com.smalaca.taskmanager.user.command.create;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.entities.UserFactory;
import com.smalaca.taskmanager.user.command.UserCommandRepository;

import java.util.Optional;

public class UserCreateCommand {
    private final UserCommandRepository userRepository;
    private final UserFactory userFactory;

    public UserCreateCommand(UserCommandRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    public Optional<Long> createUser(UserDto userDto) {
        if (userRepository.existsByFirstNameAndLastName(userDto.getFirstName(), userDto.getLastName())) {
            return Optional.empty();
        } else {
            User user = userFactory.create(userDto);
            User saved = userRepository.save(user);
            return Optional.of(saved.getId());
        }
    }

}
