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

    public Optional<Long> updateTeam(Long id, TeamDto teamDto) {
        Optional<Team> foundTeam = teamRepository.findById(id);
        if (foundTeam.isPresent()) {
            Team team = foundTeam.get();
            team.updateFromTeamDto(teamDto);
            Long updatedId = teamRepository.save(team);
            return Optional.of(updatedId);
        } else {
            return Optional.empty();
        }
    }
}
