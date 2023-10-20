package com.smalaca.taskmanager.team.command.create;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;

import java.util.Optional;

public class TeamCreateCommand {
    private final TeamRepository teamRepository;

    public TeamCreateCommand(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Long> create(TeamDto teamDto) {
        Optional<Team> foundTeam = teamRepository.findByName(teamDto.getName());

        Optional<Long> savedTeamId = Optional.empty();

        if (foundTeam.isEmpty()) {
            Team team = new Team();
            team.setName(teamDto.getName());
            Team savedTeam = teamRepository.save(team);
            savedTeamId = Optional.of(savedTeam.getId());
        }
        return savedTeamId;
    }
}
