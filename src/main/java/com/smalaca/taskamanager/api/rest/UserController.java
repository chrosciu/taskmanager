package com.smalaca.taskamanager.api.rest;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.exception.UserNotFoundException;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.entities.UserFactory;
import com.smalaca.taskamanager.repository.UserRepository;
import com.smalaca.taskmanager.user.command.UserCommandFacade;
import com.smalaca.taskmanager.user.query.UserQueryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@SuppressWarnings("checkstyle:ClassFanOutComplexity")
public class UserController {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final UserCommandFacade userCommandFacade;
    private final UserQueryFacade userQueryFacade;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userFactory = new UserFactory();
        userCommandFacade = new UserCommandFacade(userFactory, userRepository);
        userQueryFacade = new UserQueryFacade(userRepository);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersDtos = userQueryFacade.getAllUsers();

        return new ResponseEntity<>(usersDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        Optional<UserDto> userDto = userQueryFacade.getUser(id);
        return userDto
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto, UriComponentsBuilder uriComponentsBuilder) {
        Optional<Long> createdUserId = userCommandFacade.createUser(userDto);

        if (createdUserId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/user/{id}").buildAndExpand(createdUserId.get()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        User user;

        try {
            user = getUserById(id);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.update(userDto);

        User updated = userRepository.save(user);

        UserDto response = updated.asDto();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        User user;

        try {
            user = getUserById(id);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getUserById(Long id) {
        Optional<User> user;
        user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return user.get();
    }
}
