package com.smalaca.taskmanager.team.command;

import com.smalaca.taskamanager.model.entities.Team;

import java.util.Optional;

public interface TeamCommandRepository {
    Long save(Team team);

    Optional<Team> findById(Long id);

    boolean doesNotExistByName(String name);
}
