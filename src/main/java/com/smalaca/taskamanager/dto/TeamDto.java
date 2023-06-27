package com.smalaca.taskamanager.dto;

import com.smalaca.taskmanager.team.command.create.TeamCreateCommandInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class TeamDto {
    private Long id;
    private String name;
    private String codenameShort;
    private String codenameFull;
    private String description;
    private List<Long> userIds;

    public TeamCreateCommandInput getTeamCreateCommandInput() {
        return TeamCreateCommandInput.builder()
                .name(name)
                .build();
    }

    public static class TeamDtoBuilder {
        public TeamDtoBuilder codename(String shortName, String fullName) {
            return codenameShort(shortName).codenameFull(fullName);
        }
    }
}
