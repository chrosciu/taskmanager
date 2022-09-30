package com.smalaca.taskmanager.team.command;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.TeamFactory;
import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskmanager.team.command.create.TeamCreateCommand;
import com.smalaca.taskmanager.team.command.update.TeamUpdateCommand;

import java.util.Optional;

public class TeamCommandFacade {
    private final TeamCreateCommand teamCreateCommand;
    private final TeamUpdateCommand teamUpdateCommand;

    public TeamCommandFacade(TeamRepository teamRepository, TeamFactory teamFactory) {
        teamCreateCommand = new TeamCreateCommand(teamRepository, teamFactory);
        teamUpdateCommand = new TeamUpdateCommand(teamRepository);
    }

    public Optional<Long> create(TeamDto teamDto) {
        return teamCreateCommand.create(teamDto);
    }

    public Optional<TeamDto> update(Long id, TeamDto teamDto) {
        return teamUpdateCommand.update(id, teamDto);
    }
}
