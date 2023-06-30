package com.smalaca.taskmanager.team.command.create;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamCreateCommandInput {
    private final String name;
}
