package com.smalaca.taskmanager.user.command.create;

import com.smalaca.taskamanager.model.enums.TeamRole;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCreateCommandInput {
    private final String firstName;
    private final String lastName;
    private final String login;
    private final String password;
    private final TeamRole teamRole;
}
