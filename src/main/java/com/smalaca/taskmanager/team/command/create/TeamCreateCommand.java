package com.smalaca.taskmanager.team.command.create;

import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskmanager.team.command.TeamCommandRepository;

import java.util.Optional;

public class TeamCreateCommand {
    private final TeamCommandRepository teamRepository;

    public TeamCreateCommand(TeamCommandRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Long> createTeam(TeamCreateCommandInput input) {
        if (teamRepository.existsByName(input.getName())) {
            return Optional.empty();
        } else {
            Team team = Team.createFromInput(input);
            Long savedId = teamRepository.save(team);
            return Optional.of(savedId);
        }
    }
}
