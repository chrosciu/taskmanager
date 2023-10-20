package com.smalaca.taskamanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamDto {
    private Long id;
    private String name;
    private String codenameShort;
    private String codenameFull;
    private String description;
    private List<Long> userIds = new ArrayList<>();

    public static class TeamDtoBuilder {
        public TeamDtoBuilder codename(String shortName, String fullName) {
            return codenameShort(shortName).codenameFull(fullName);
        }
    }
    @Deprecated
    public void setCodenameShort(String codenameShort) {
        this.codenameShort = codenameShort;
    }

    @Deprecated
    public void setCodenameFull(String codenameFull) {
        this.codenameFull = codenameFull;
    }

}
