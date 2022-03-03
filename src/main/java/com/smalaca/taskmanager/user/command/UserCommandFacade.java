package com.smalaca.taskmanager.user.command;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;

import java.util.Optional;

public class UserCommandFacade {
    private final UserFactory userFactory;
    private final UserCommandRepository userRepository;

    public UserCommandFacade(UserFactory userFactory, UserCommandRepository userRepository) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
    }

    public static UserCommandFacade create(UserCommandRepository userRepository) {
        return new UserCommandFacade(new UserFactory(), userRepository);
    }

    public Optional<Long> create(UserDto userDto) {
        if (userRepository.existsByUserName(userDto.getFirstName(), userDto.getLastName())) {
            return Optional.empty();
        } else {
            User user = userFactory.create(userDto);
            return Optional.of(userRepository.save(user));
        }
    }

    public Optional<Long> update(Long id, UserDto userDto) {
        Optional<User> found = userRepository.findById(id);

        if (found.isPresent()) {
            User user = found.get();
            user.update(userDto);
            return Optional.of(userRepository.save(user));
        }

        return Optional.empty();
    }
}
