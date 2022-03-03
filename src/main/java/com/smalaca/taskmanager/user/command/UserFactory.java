package com.smalaca.taskmanager.user.command;

import com.smalaca.taskamanager.model.entities.User;

class UserFactory {
    User create(UserCreateCommand command) {
        User user = new User();
        user.setTeamRole(command.teamRole());
        user.setUserName(command.userName());
        user.setLogin(command.login());
        user.setPassword(command.password());
        return user;
    }
}
