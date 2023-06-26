package com.smalaca.taskmanager.team.command;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskmanager.team.command.create.TeamCreateCommand;
import com.smalaca.taskmanager.team.command.update.TeamUpdateCommand;

import java.util.Optional;

public class TeamCommandFacade {
    private final TeamCreateCommand teamCreateCommand;
    private final TeamUpdateCommand teamUpdateCommand;

    public TeamCommandFacade(TeamCommandRepository teamRepository) {
        teamCreateCommand = new TeamCreateCommand(teamRepository);
        teamUpdateCommand = new TeamUpdateCommand(teamRepository);
    }

    public Optional<Long> create(TeamDto teamDto) {
        return teamCreateCommand.create(teamDto);
    }

    public Optional<Long> update(Long id, TeamDto teamDto) {
        return teamUpdateCommand.update(id, teamDto);
    }
}
