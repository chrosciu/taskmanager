package com.smalaca.taskmanager.team.command;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskmanager.team.command.create.TeamCreateCommand;
import com.smalaca.taskmanager.team.command.update.TeamUpdateCommand;
import java.util.Optional;

public class TeamCommands {
    private final TeamRepository teamRepository;
    private final TeamCreateCommand teamCreateCommand;
    private final TeamUpdateCommand teamUpdateCommand;

    public TeamCommands(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        teamCreateCommand = new TeamCreateCommand(teamRepository);
        teamUpdateCommand = new TeamUpdateCommand(teamRepository);
    }

    public Optional<Long> create(TeamDto teamDto) {
        return teamCreateCommand.create(teamDto);
    }

    public Optional<TeamDto> update(Long id, TeamDto teamDto) {
        return teamUpdateCommand.update(id, teamDto);
    }
}
