package com.smalaca.taskmanager.team.command;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;

import java.util.Optional;

public class TeamCommandFacade {
    private final TeamRepository teamRepository;

    public TeamCommandFacade(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Long> createTeam(TeamDto teamDto) {
        if (teamRepository.findByName(teamDto.getName()).isEmpty()) {
            Team team = Team.createFromTeamDto(teamDto);
            Team saved = teamRepository.save(team);
            return Optional.of(saved.getId());
        } else {
            return Optional.empty();
        }
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
