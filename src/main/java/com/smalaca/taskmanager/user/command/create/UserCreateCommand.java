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
        Optional<Long> createdUserId = Optional.empty();

        if (!exists(userDto)) {
            User user = User.createFromUserDto(userDto);

            User saved = userRepository.save(user);
            createdUserId = Optional.of(saved.getId());
        }
        return createdUserId;
    }

    private boolean exists(UserDto userDto) {
        return !userRepository.findByUserNameFirstNameAndUserNameLastName(userDto.getFirstName(), userDto.getLastName()).isEmpty();
    }
}
