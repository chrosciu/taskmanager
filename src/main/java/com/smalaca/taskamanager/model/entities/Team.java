package com.smalaca.taskamanager.model.entities;

import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.model.embedded.Codename;
import com.smalaca.taskmanager.team.command.create.TeamCreateCommandInput;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Codename codename;

    private String description;

    @OneToMany
    private List<User> members = new ArrayList<>();

    @ManyToOne
    private Project project;

    public static Team createFromInput(TeamCreateCommandInput input) {
        Team team = new Team();
        team.setName(input.getName());
        return team;
    }

    public void updateFromTeamDto(TeamDto teamDto) {
        if (teamDto.getName() != null) {
            setName(teamDto.getName());
        }

        if (teamDto.getCodenameShort() != null && teamDto.getCodenameFull() != null) {
            setCodename(new Codename(teamDto.getCodenameShort(), teamDto.getCodenameFull()));
        }

        if (teamDto.getDescription() != null) {
            setDescription(teamDto.getDescription());
        }
    }

    public boolean hasCodename() {
        return getCodename() != null;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembers(List<User> members) {
        this.members = new ArrayList<>(members);
    }

    @Deprecated
    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(User user) {
        if (!members.contains(user)) {
            throw new RuntimeException();
        }
        members.remove(user);
    }

    @Deprecated
    public Codename getCodename() {
        return codename;
    }

    public void setCodename(Codename codename) {
        this.codename = codename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Deprecated
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Team team = (Team) o;

        return new EqualsBuilder()
                .append(id, team.id)
                .append(name, team.name)
                .append(codename, team.codename)
                .append(description, team.description)
                .isEquals();
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(codename)
                .append(description)
                .toHashCode();
    }

    private List<Long> getMemberIds() {
        return members.stream().map(User::getId).collect(toList());
    }

    public TeamDto asTeamDto() {
        TeamDto.TeamDtoBuilder builder = TeamDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .userIds(getMemberIds());

        if (hasCodename()) {
            builder.codename(codename.getShortName(), codename.getFullName());
        }

        return builder.build();
    }

}
