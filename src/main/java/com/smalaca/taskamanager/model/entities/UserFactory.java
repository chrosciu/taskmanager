package com.smalaca.taskamanager.model.entities;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.embedded.UserName;

public class UserFactory {

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
