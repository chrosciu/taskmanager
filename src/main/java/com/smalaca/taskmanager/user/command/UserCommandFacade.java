package com.smalaca.taskmanager.user.command;

import com.smalaca.taskamanager.api.rest.UserController;
import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.entities.UserFactory;
import com.smalaca.taskamanager.repository.UserRepository;

import java.util.Optional;

public class UserCommandFacade {
    private final UserFactory userFactory;
    private final UserRepository userRepository;

    public UserCommandFacade(UserFactory userFactory, UserRepository userRepository) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
    }

    public Optional<Long> create(UserDto userDto) {
        Optional<Long> id = Optional.empty();

        if (!exists(userDto)) {
            User user = userFactory.create(userDto);
            User saved = userRepository.save(user);
            id = Optional.of(saved.getId());
        }

        return id;
    }

    private boolean exists(UserDto userDto) {
        return !userRepository.findByUserNameFirstNameAndUserNameLastName(userDto.getFirstName(), userDto.getLastName()).isEmpty();
    }

    public Optional<Long> update(Long id, UserDto userDto) {
        Optional<User> found = userRepository.findById(id);
        Optional<Long> updateUserId = Optional.empty();

        if (found.isPresent()) {
            User user = found.get();
            user.update(userDto);
            userRepository.save(user);
            updateUserId = Optional.of(user.getId());
        }
        return updateUserId;
    }
}
