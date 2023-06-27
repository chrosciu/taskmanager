package com.smalaca.taskmanager.team.command.create;

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

    public Optional<Long> create(TeamCreateCommandInput input) {
        if (teamRepository.existsByName(input.getName())) {
            return Optional.empty();
        } else {
            Team team = teamFactory.createTeam(input);
            Long savedId = teamRepository.save(team);
            return Optional.of(savedId);
        }
    }
}
