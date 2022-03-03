package com.smalaca.taskmanager.team.query;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class TeamQueryFacade {
    private final TeamRepository teamRepository;

    public TeamQueryFacade(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamDto> findAll() {
        return StreamSupport.stream(teamRepository.findAll().spliterator(), false)
                .map(Team::asDto)
                .collect(toList());
    }
}
