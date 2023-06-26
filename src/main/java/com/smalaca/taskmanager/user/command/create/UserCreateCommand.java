package com.smalaca.taskmanager.user.command.create;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskmanager.user.command.UserCommandRepository;

import java.util.Optional;

public class UserCreateCommand {

    private final UserCommandRepository userRepository;

    public UserCreateCommand(UserCommandRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Long> createUser(UserDto userDto) {
        if (userRepository.existsByFirstNameAndLastName(userDto.getFirstName(), userDto.getLastName())) {
            return Optional.empty();
        } else {
            User user = User.createFromUserDto(userDto);
            Long savedId = userRepository.save(user);
            return Optional.of(savedId);
        }
    }

}
