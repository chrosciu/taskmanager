package com.smalaca.taskmanager.user.command.update;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.UserRepository;

import java.util.Optional;

public class UserUpdateCommand {
    private final UserRepository userRepository;

    public UserUpdateCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDto> updateUser(Long id, UserDto userDto) {
        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isEmpty()) {
            return Optional.empty();
        }

        User user = foundUser.get();

        user.updateFromUserDto(userDto);

        User updated = userRepository.save(user);

        UserDto response = updated.asDto();
        return Optional.of(response);
    }
}
