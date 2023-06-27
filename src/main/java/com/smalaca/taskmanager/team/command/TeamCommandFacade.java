package com.smalaca.taskmanager.team.command;

import com.smalaca.taskmanager.team.command.create.TeamCreateCommand;
import com.smalaca.taskmanager.team.command.create.TeamCreateCommandInput;
import com.smalaca.taskmanager.team.command.update.TeamUpdateCommand;
import com.smalaca.taskmanager.team.command.update.TeamUpdateCommandInput;

import java.util.Optional;

public class TeamCommandFacade {
    private final TeamCreateCommand teamCreateCommand;
    private final TeamUpdateCommand teamUpdateCommand;

    public TeamCommandFacade(TeamCommandRepository teamRepository) {
        teamCreateCommand = new TeamCreateCommand(teamRepository);
        teamUpdateCommand = new TeamUpdateCommand(teamRepository);
    }

    public Optional<Long> create(TeamCreateCommandInput input) {
        return teamCreateCommand.create(input);
    }

    public Optional<Long> update(TeamUpdateCommandInput input) {
        return teamUpdateCommand.update(input);
    }
}
