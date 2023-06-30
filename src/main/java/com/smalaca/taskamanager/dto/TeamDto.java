package com.smalaca.taskamanager.dto;

import com.smalaca.taskmanager.team.command.create.TeamCreateCommandInput;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamDto {
    private final Long id;
    private final String name;
    private final String codenameShort;
    private final String codenameFull;
    private final String description;
    private final List<Long> userIds;

    public TeamCreateCommandInput asTeamCreateCommandInput() {
        return TeamCreateCommandInput.builder()
                .name(getName())
                .build();
    }

    public static class TeamDtoBuilder {
        public TeamDtoBuilder codename(String shortName, String fullName) {
            return codenameShort(shortName).codenameFull(fullName);
        }
    }
}
