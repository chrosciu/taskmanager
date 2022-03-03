package com.smalaca.taskmanager.user.command;

import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.enums.TeamRole;
import lombok.Builder;

@Builder
public class UserCreateCommand {
    private final String firstName;
    private final String lastName;
    private final String login;
    private final String password;
    private final String teamRole;

    TeamRole teamRole() {
        return TeamRole.valueOf(teamRole);
    }

    UserName userName() {
        return new UserName(firstName, lastName);
    }

    String login() {
        return login;
    }

    String password() {
        return password;
    }

    String firstName() {
        return firstName;
    }

    String lastName() {
        return lastName;
    }
}
