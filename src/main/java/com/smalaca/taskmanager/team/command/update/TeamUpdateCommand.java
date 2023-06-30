package com.smalaca.taskmanager.team.command.update;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;

import java.util.Optional;

public class TeamUpdateCommand {

    private final TeamRepository teamRepository;

    public TeamUpdateCommand(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<TeamDto> updateTeam(Long id, TeamDto teamDto) {
        Optional<Team> foundTeam = teamRepository.findById(id);
        if (foundTeam.isPresent()) {
            Team team = foundTeam.get();
            team.updateFromTeamDto(teamDto);
            Team updated = teamRepository.save(team);
            return Optional.of(updated.asTeamDto());
        } else {
            return Optional.empty();
        }
    }
}
