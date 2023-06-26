package com.smalaca.taskmanager.team.command.create;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.entities.Team;

class TeamFactory {

    public Team createTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setName(teamDto.getName());
        return team;
    }
}
