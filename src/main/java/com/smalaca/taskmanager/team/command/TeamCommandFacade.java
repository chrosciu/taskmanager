package com.smalaca.taskmanager.team.command;

import com.smalaca.taskamanager.api.rest.TeamController;
import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;

import java.util.Optional;

public class TeamCommandFacade {
    private final TeamRepository teamRepository;

    public TeamCommandFacade(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Long> create(TeamDto teamDto) {
        boolean present = teamRepository.findByName(teamDto.getName()).isPresent();
        Optional<Long> id = Optional.empty();
        if (!present) {
            Team team = new Team(teamDto.getName());
            Team saved = teamRepository.save(team);
            id = Optional.of(saved.getId());
        }
        return id;
    }

    public Optional<Long> update(Long id, TeamDto teamDto) {
        Optional<Team> found = teamRepository.findById(id);

        if (found.isPresent()) {
            Team team = found.get();
            team.update(teamDto);
            teamRepository.save(team);
            return Optional.of(team.getId());
        }

        return Optional.empty();
    }
}
