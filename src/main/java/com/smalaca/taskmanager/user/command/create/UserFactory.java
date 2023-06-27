package com.smalaca.taskmanager.user.command.create;

import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.entities.User;

class UserFactory {

    public User create(UserCreateCommandInput input) {
        User user = new User();
        user.setTeamRole(input.getTeamRole());
        UserName userName = new UserName(input.getFirstName(), input.getLastName());
        user.setUserName(userName);
        user.setLogin(input.getLogin());
        user.setPassword(input.getPassword());
        return user;
    }
}
