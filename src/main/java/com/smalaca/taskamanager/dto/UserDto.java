package com.smalaca.taskamanager.dto;

import com.smalaca.taskamanager.model.enums.TeamRole;
import com.smalaca.taskmanager.user.command.create.UserCreateCommandInput;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String login;
    private final String password;
    private final String phoneNumber;
    private final String phonePrefix;
    private final String emailAddress;
    private final String teamRole;

    private TeamRole asTeamRole() {
        return TeamRole.valueOf(getTeamRole());
    }

    public UserCreateCommandInput getUserCreateCommandInput() {
        return UserCreateCommandInput.builder()
                .firstName(firstName)
                .lastName(lastName)
                .login(login)
                .password(password)
                .teamRole(asTeamRole())
                .build();
    }

    public static class UserDtoBuilder {
        public UserDtoBuilder phone(String prefix, String number) {
            return phonePrefix(prefix).phoneNumber(number);
        }
    }
}
