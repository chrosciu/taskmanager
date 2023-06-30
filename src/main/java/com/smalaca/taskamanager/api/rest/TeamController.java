package com.smalaca.taskamanager.api.rest;


import com.google.common.collect.Iterables;
import com.smalaca.taskamanager.dto.TeamDto;
import com.smalaca.taskamanager.dto.TeamMembersDto;
import com.smalaca.taskamanager.exception.TeamNotFoundException;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskamanager.repository.UserRepository;
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
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/team")
@SuppressWarnings("checkstyle:ClassFanOutComplexity")
public class TeamController {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamController(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> findAll() {
        List<TeamDto> teams = StreamSupport.stream(teamRepository.findAll().spliterator(), false)
                .map(Team::asTeamDto)
                .collect(toList());

        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<TeamDto> findById(@PathVariable Long id) {
        Optional<Team> foundTeam = teamRepository.findById(id);

        if (foundTeam.isPresent()) {
            Team team = foundTeam.get();
            TeamDto dto = team.asTeamDto();

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createTeam(@RequestBody TeamDto teamDto, UriComponentsBuilder uriComponentsBuilder) {
        if (teamRepository.findByName(teamDto.getName()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Team team = Team.createFromTeamDto(teamDto);
            Team saved = teamRepository.save(team);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponentsBuilder.path("/team/{id}").buildAndExpand(saved.getId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        Team team;

        try {
            team = getTeamById(id);
        } catch (TeamNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        team.updateFromTeamDto(teamDto);

        Team updated = teamRepository.save(team);

        return new ResponseEntity<>(updated.asTeamDto(), HttpStatus.OK);
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
