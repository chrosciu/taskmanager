package com.smalaca.taskamanager.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamDto {
    private final Long id;
    private final String name;
    private final String codenameShort;
    private final String codenameFull;
    private final String description;
    private final List<Long> userIds;

    public static class TeamDtoBuilder {
        public TeamDtoBuilder codename(String shortName, String fullName) {
            return codenameShort(shortName).codenameFull(fullName);
        }
    }
}
