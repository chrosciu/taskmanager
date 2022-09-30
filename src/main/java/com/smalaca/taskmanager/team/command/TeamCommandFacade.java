package com.smalaca.taskmanager.team.command;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.model.entities.TeamFactory;
import com.smalaca.taskamanager.repository.TeamRepository;

import java.util.Optional;

public class TeamCommandFacade {
    private final TeamRepository teamRepository;
    private final TeamFactory teamFactory;

    public TeamCommandFacade(TeamRepository teamRepository, TeamFactory teamFactory) {
        this.teamRepository = teamRepository;
        this.teamFactory = teamFactory;
    }

    public Optional<Long> create(TeamDto teamDto) {
        Optional<Team> foundTeam = teamRepository.findByName(teamDto.getName());
        if (foundTeam.isEmpty()) {
            Team team = teamFactory.createTeam(teamDto);
            Team saved = teamRepository.save(team);
            return Optional.of(saved.getId());
        } else {
            return Optional.empty();
        }
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
