package com.kingsleague.service;


import com.kingsleague.model.Player;
import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.PlayerRepository;
import com.kingsleague.repository.interfaces.TeamRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class TeamService {
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.get(id);
    }

    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        return teamRepository.getAll();
    }

    public Optional<Team> getTeamByName(String name) {
        return Optional.ofNullable(teamRepository.getByName(name).orElse(null));
    }

    public void addTeam(Team team) {
        teamRepository.add(team);
    }
    public void updateTeam(Team team) {
        teamRepository.update(team);
    }
    public void deleteTeam(Long id ) {
        teamRepository.delete(id);

    }

    public void deleteTeamByName(String name) {
        Optional<Team> teamOptional = teamRepository.getByName(name);
        if (teamOptional.isPresent()) {
            deleteTeam(teamOptional.get().getId());
        } else {
            throw new IllegalArgumentException("Team not found with name: " + name);
        }
    }


    public void addPlayerToTeam(String teamName, String playerUsername) {
        Team team = teamRepository.getByName(teamName)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with name: " + teamName));

        Player player = playerRepository.getByUsername(playerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with username: " + playerUsername));

        team.getPlayers().add(player);
        player.setTeam(team);
        teamRepository.update(team);
        playerRepository.update(player);
    }

    public void removePlayerFromTeam(String teamName, String playerUsername) {
        Team team = teamRepository.getByName(teamName)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with name: " + teamName));

        Player player = playerRepository.getByUsername(playerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with username: " + playerUsername));

        if (team.getPlayers().remove(player)) {
            player.setTeam(null);
            teamRepository.update(team);
            playerRepository.update(player);
        } else {
            throw new IllegalArgumentException("Player is not a member of this team");
        }
    }


}
