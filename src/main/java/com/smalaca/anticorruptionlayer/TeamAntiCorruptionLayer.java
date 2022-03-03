package com.smalaca.anticorruptionlayer;

import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskmanager.team.command.TeamCommandRepository;
import com.smalaca.taskmanager.team.command.TeamDomain;

import java.util.Optional;

public class TeamAntiCorruptionLayer implements TeamCommandRepository {
    private final TeamRepository teamRepository;

    public TeamAntiCorruptionLayer(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Long save(TeamDomain team) {
        return teamRepository.save(team.getTeam()).getId();
    }

    @Override
    public Optional<TeamDomain> findById(Long id) {
        return teamRepository.findById(id).map(TeamDomain::new);
    }

    @Override
    public boolean doesNotExistByName(String name) {
        return teamRepository.findByName(name).isEmpty();
    }
}
