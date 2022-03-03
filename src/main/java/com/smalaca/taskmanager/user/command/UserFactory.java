package com.smalaca.taskmanager.user.command;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.entities.User;

class UserFactory {
    User create(UserDto userDto) {
        User user = new User();
        user.setTeamRole(userDto.asTeamRole());
        user.setUserName(new UserName(userDto.getFirstName(), userDto.getLastName()));
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
