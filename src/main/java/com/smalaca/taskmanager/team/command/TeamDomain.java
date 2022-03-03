package com.smalaca.taskmanager.team.command;

import com.smalaca.taskamanager.model.embedded.Codename;
import com.smalaca.taskamanager.model.entities.Team;

public class TeamDomain {
    private final Team team;

    public TeamDomain(String name) {
        team = new Team(name);
    }

    @Deprecated
    public TeamDomain(Team team) {
        this.team = team;
    }

    @Deprecated
    public Team getTeam() {
        return team;
    }

    void update(TeamUpdateCommand command) {
        if (command.getName() != null) {
            team.setName(command.getName());
        }

        if (command.getCodenameShort() != null && command.getCodenameFull() != null) {
            Codename codename = new Codename();
            codename.setShortName(command.getCodenameShort());
            codename.setFullName(command.getCodenameFull());
            team.setCodename(codename);
        }

        if (command.getDescription() != null) {
            team.setDescription(command.getDescription());
        }
    }
}
