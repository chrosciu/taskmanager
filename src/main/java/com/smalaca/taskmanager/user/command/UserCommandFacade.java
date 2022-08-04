package com.smalaca.taskmanager.user.command;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.UserRepository;

import java.util.Optional;

public class UserCommandFacade {
    private final UserRepository userRepository;

    public UserCommandFacade(UserRepository userRepository) {
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
