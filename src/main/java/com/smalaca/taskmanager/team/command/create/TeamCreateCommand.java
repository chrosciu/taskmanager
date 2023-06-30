package com.smalaca.taskmanager.team.command.create;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskmanager.team.command.TeamCommandRepository;

import java.util.Optional;

public class TeamCreateCommand {
    private final TeamCommandRepository teamRepository;

    public TeamCreateCommand(TeamCommandRepository teamRepository) {
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
}
