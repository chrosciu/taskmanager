package com.smalaca.taskmanager.user.query;

import com.smalaca.taskamanager.api.rest.UserController;
import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserQueryFacade {
    private final UserRepository userRepository;

    public UserQueryFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll() {
        List<UserDto> usersDtos = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            usersDtos.add(user.asDto());
        }

        return usersDtos;
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(User::asDto);
    }
}
