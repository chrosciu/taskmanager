package com.smalaca.taskmanager.team.command.create;

import com.smalaca.taskamanager.model.entities.Team;

class TeamFactory {

    public Team createTeam(TeamCreateCommandInput input) {
        Team team = new Team();
        team.setName(input.getName());
        return team;
    }
}
