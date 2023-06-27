package com.smalaca.taskmanager.user.command.create;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.entities.User;

class UserFactory {

    public User create(UserDto userDto) {
        User user = new User();
        user.setTeamRole(userDto.asTeamRole());
        UserName userName = new UserName(userDto.getFirstName(), userDto.getLastName());
        user.setUserName(userName);
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
