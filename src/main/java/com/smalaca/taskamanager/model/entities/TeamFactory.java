package com.smalaca.taskamanager.model.entities;

import com.smalaca.taskamanager.dto.TeamDto;

public class TeamFactory {

    public Team create(TeamDto teamDto) {
        Team team = new Team();
        team.setName(teamDto.getName());
        return team;
    }
}
