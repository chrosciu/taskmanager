package com.smalaca.taskmanager.team.command;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;

import java.util.Optional;

public class TeamCommandFacade {
    private final TeamCommandRepository teamRepository;

    public TeamCommandFacade(TeamCommandRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Long> create(String name) {
        Optional<Long> id = Optional.empty();

        if (teamRepository.doesNotExistByName(name)) {
            Team team = new Team(name);
            Long savedId = teamRepository.save(team);
            id = Optional.of(savedId);
        }
        return id;
    }

    public Optional<Long> update(Long id, TeamDto teamDto) {
        Optional<Team> found = teamRepository.findById(id);

        if (found.isPresent()) {
            Team team = found.get();
            team.update(teamDto);
            Long savedId = teamRepository.save(team);
            return Optional.of(savedId);
        }

        return Optional.empty();
    }
}
