package com.smalaca.taskmanager.team.command.create;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskmanager.team.command.TeamCommandRepository;

import java.util.Optional;

public class TeamCreateCommand {
    private final TeamCommandRepository teamRepository;
    private final TeamFactory teamFactory;

    public TeamCreateCommand(TeamCommandRepository teamRepository) {
        this.teamRepository = teamRepository;
        this.teamFactory = new TeamFactory();
    }

    public Optional<Long> create(TeamDto teamDto) {
        if (teamRepository.existsByName(teamDto.getName())) {
            return Optional.empty();
        } else {
            Team team = teamFactory.createTeam(teamDto);
            Long savedId = teamRepository.save(team);
            return Optional.of(savedId);
        }
    }
}
