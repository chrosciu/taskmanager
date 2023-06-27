package com.smalaca.taskmanager.user.command;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskmanager.user.command.create.UserCreateCommand;
import com.smalaca.taskmanager.user.command.create.UserCreateCommandInput;
import com.smalaca.taskmanager.user.command.update.UserUpdateCommand;

import java.util.Optional;

public class UserCommandFacade {
    private final UserCreateCommand userCreateCommand;
    private final UserUpdateCommand userUpdateCommand;

    public UserCommandFacade(UserCommandRepository userRepository) {
        this.userCreateCommand = new UserCreateCommand(userRepository);
        this.userUpdateCommand = new UserUpdateCommand(userRepository);
    }

    public Optional<Long> createUser(UserCreateCommandInput input) {
        return userCreateCommand.createUser(input);
    }

    public Optional<Long> updateUser(Long id, UserDto userDto) {
        return userUpdateCommand.updateUser(id, userDto);
    }
}
