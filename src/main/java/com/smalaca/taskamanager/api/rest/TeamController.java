package com.smalaca.taskamanager.api.rest;


import com.google.common.collect.Iterables;
import com.smalaca.anticorruptionlayer.TeamAntiCorruptionLayer;
import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.dto.TeamMembersDto;
import com.smalaca.taskamanager.exception.TeamNotFoundException;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskamanager.repository.UserRepository;
import com.smalaca.taskmanager.team.command.TeamCommandFacade;
import com.smalaca.taskmanager.team.command.create.TeamCreateCommandInput;
import com.smalaca.taskmanager.team.command.update.TeamUpdateCommandInput;
import com.smalaca.taskmanager.team.query.TeamQueryFacade;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
@SuppressWarnings("checkstyle:ClassFanOutComplexity")
public class TeamController {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamCommandFacade teamCommandFacade;
    private final TeamQueryFacade teamQueryFacade;

    public TeamController(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        teamCommandFacade = new TeamCommandFacade(
                new TeamAntiCorruptionLayer(teamRepository));
        teamQueryFacade = new TeamQueryFacade(teamRepository);
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> findAll() {
        List<TeamDto> teams = teamQueryFacade.findAll();

        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<TeamDto> findById(@PathVariable Long id) {
        return teamQueryFacade.findById(id)
                .map(teamDto -> new ResponseEntity<>(teamDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Void> createTeam(@RequestBody TeamDto teamDto, UriComponentsBuilder uriComponentsBuilder) {
        TeamCreateCommandInput input = teamDto.getTeamCreateCommandInput();
        Optional<Long> id = teamCommandFacade.create(input);

        if (id.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/team/{id}").buildAndExpand(id.get()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        if (null == teamDto) {
            teamDto = TeamDto.builder().build();
        }
        TeamUpdateCommandInput input = teamDto.getTeamUpdateCommandInput(id);
        Optional<Long> updatedTeamId = teamCommandFacade.update(input);

        return updatedTeamId
                .flatMap(teamQueryFacade::findById)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/members")
    @Transactional
    public ResponseEntity<Void> addTeamMembers(@PathVariable Long id, @RequestBody TeamMembersDto dto) {
        try {
            Team team = getTeamById(id);
            Iterable<User> users = findUsers(dto);

            if (Iterables.size(users) != dto.getUserIds().size()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            users.forEach(user -> {
                user.addToTeam(team);
                team.addMember(user);
            });

            teamRepository.save(team);
            userRepository.saveAll(users);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (TeamNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/members")
    @Transactional
    public ResponseEntity<Void> removeTeamMembers(@PathVariable Long id, @RequestBody TeamMembersDto dto) {
        try {
            Team team = getTeamById(id);
            Iterable<User> users = findUsers(dto);

            users.forEach(user -> {
                if (user.getTeams().contains(team)) {
                    user.removeFrom(team);
                    team.removeMember(user);
                }
            });

            teamRepository.save(team);
            userRepository.saveAll(users);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (TeamNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private Iterable<User> findUsers(TeamMembersDto dto) {
        return userRepository.findAllById(dto.getUserIds());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        Team team;

        try {
            team = getTeamById(id);
        } catch (TeamNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        teamRepository.delete(team);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Team getTeamById(Long id) {
        Optional<Team> team = teamRepository.findById(id);

        if (team.isEmpty()) {
            throw new TeamNotFoundException();
        }

        return team.get();
    }
}
