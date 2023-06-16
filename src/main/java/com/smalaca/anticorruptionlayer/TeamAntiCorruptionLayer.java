package com.smalaca.anticorruptionlayer;

import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskmanager.team.command.TeamCommandRepository;

import java.util.Optional;

public class TeamAntiCorruptionLayer implements TeamCommandRepository {
    private final TeamRepository teamRepository;

    public TeamAntiCorruptionLayer(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public boolean existsByName(String name) {
        return teamRepository.findByName(name).isPresent();
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }
}
