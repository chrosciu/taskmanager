package com.smalaca.taskmanager.team.command.create;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.model.entities.TeamFactory;
import com.smalaca.taskamanager.repository.TeamRepository;
import java.util.Optional;

public class TeamCreateCommand {
    private final TeamRepository teamRepository;
    private final TeamFactory teamFactory;

    public TeamCreateCommand(TeamRepository teamRepository, TeamFactory teamFactory) {
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
}
