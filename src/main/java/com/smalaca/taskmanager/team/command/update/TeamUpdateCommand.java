package com.smalaca.taskmanager.team.command.update;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskmanager.team.command.TeamCommandRepository;

import java.util.Optional;

public class TeamUpdateCommand {
    private final TeamCommandRepository teamRepository;

    public TeamUpdateCommand(TeamCommandRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<TeamDto> update(Long id, TeamDto teamDto) {
        Optional<Team> found = teamRepository.findById(id);
        Optional<TeamDto> updated = Optional.empty();
        if (found.isPresent()) {
            Team team = found.get();

            team.update(teamDto);

            updated = Optional.of(teamRepository.save(team).asTeamDto());
        }
        return updated;
    }
}
