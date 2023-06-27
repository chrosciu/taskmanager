package com.smalaca.taskmanager.team.command.update;

import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskmanager.team.command.TeamCommandRepository;

import java.util.Optional;

public class TeamUpdateCommand {
    private final TeamCommandRepository teamRepository;

    public TeamUpdateCommand(TeamCommandRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Long> update(TeamUpdateCommandInput input) {
        Optional<Team> found = teamRepository.findById(input.getId());
        Optional<Long> updatedTeamId = Optional.empty();
        if (found.isPresent()) {
            Team team = found.get();

            team.update(input);

            updatedTeamId = Optional.of(teamRepository.save(team));
        }
        return updatedTeamId;
    }
}
