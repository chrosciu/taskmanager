package com.smalaca.taskamanager.model.entities;

import com.smalaca.taskamanager.dto.UserDto;
import com.smalaca.taskamanager.model.embedded.EmailAddress;
import com.smalaca.taskamanager.model.embedded.PhoneNumber;
import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.enums.TeamRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuppressWarnings("MethodCount")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;

    @Embedded
    private UserName userName;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private EmailAddress emailAddress;

    @Enumerated(EnumType.STRING)
    private TeamRole teamRole;

    @OneToMany
    private List<Team> teams = new ArrayList<>();

    @Deprecated
    public UserName getUserName() {
        return userName;
    }

    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Deprecated
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Deprecated
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Deprecated
    public TeamRole getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(TeamRole teamRole) {
        this.teamRole = teamRole;
    }

    public Long getId() {
        return id;
    }

    public void setTeams(List<Team> teams) {
        this.teams = new ArrayList<>(teams);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void addToTeam(Team team) {
        teams.add(team);
    }

    public void removeFrom(Team team) {
        if (!teams.contains(team)) {
            throw new RuntimeException();
        }
        teams.remove(team);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(userName, user.userName)
                .append(login, user.login)
                .append(password, user.password)
                .append(phoneNumber, user.phoneNumber)
                .append(emailAddress, user.emailAddress)
                .append(teamRole, user.teamRole)
                .isEquals();
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userName)
                .append(login)
                .append(password)
                .append(phoneNumber)
                .append(emailAddress)
                .append(teamRole)
                .toHashCode();
    }

    public UserDto asDto() {
        UserDto.UserDtoBuilder builder = UserDto.builder()
        .id(id)
        .firstName(userName.getFirstName())
        .lastName(userName.getLastName())
        .login(login)
        .password(password);

        if (hasTeamRole()) {
            builder.teamRole(getTeamRole().name());
        }

        if (hasPhoneNumber()) {
            builder.phone(phoneNumber.getPrefix(), phoneNumber.getNumber());
        }

        if (hasEmailAddress()) {
            builder.emailAddress(emailAddress.getEmailAddress());
        }

        return builder.build();
    }

    public boolean hasPhoneNumber() {
        return getPhoneNumber() != null;
    }

    public boolean hasEmailAddress() {
        return getEmailAddress() != null;
    }

    public boolean hasTeamRole() {
        return getTeamRole() != null;
    }

    public void update(UserDto userDto) {
        if (userDto.getPassword() != null) {
            this.password = userDto.getPassword();
        }

        if (userDto.getPhoneNumber() != null) {
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setPrefix(userDto.getPhonePrefix());
            phoneNumber.setNumber(userDto.getPhoneNumber());
            this.phoneNumber = phoneNumber;
        }

        if (userDto.getEmailAddress() != null) {
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.setEmailAddress(userDto.getEmailAddress());
            this.emailAddress = emailAddress;
        }

        if (userDto.getTeamRole() != null) {
            TeamRole teamRole = TeamRole.valueOf(userDto.getTeamRole());
            this.teamRole = teamRole;
        }

        if (userDto.getLogin() != null) {
            this.login = userDto.getLogin();
        }
    }
}
