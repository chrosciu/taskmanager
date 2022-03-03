package com.smalaca.taskmanager.team.command;

import java.util.Optional;

public interface TeamCommandRepository {
    Long save(TeamDomain team);

    Optional<TeamDomain> findById(Long id);

    boolean doesNotExistByName(String name);
}
