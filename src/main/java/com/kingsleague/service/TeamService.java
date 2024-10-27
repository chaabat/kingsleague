package com.kingsleague.service;

import com.kingsleague.model.Player;
import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.PlayerRepository;
import com.kingsleague.repository.interfaces.TeamRepository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Transactional
public class TeamService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Team> get(Long id) {
        return teamRepository.get(id);
    }

    @Transactional(readOnly = true)
    public List<Team> getAll() {
        return teamRepository.getAll();
    }

    public void add(Team team) {
        teamRepository.add(team);
    }

    public void update(Team team) {
        teamRepository.update(team);
    }

    public void delete(Long id) {
        teamRepository.delete(id);
    }

    @Transactional
    public void addPlayerToTeam(String teamName, String playerUsername) {
        Team team = teamRepository.getByName(teamName)
            .orElseThrow(() -> new IllegalArgumentException("Team not found with name: " + teamName));

        Player player = playerRepository.getByUsername(playerUsername)
            .orElseThrow(() -> new IllegalArgumentException("Player not found with username: " + playerUsername));

        // Remove player from their current team, if any
        if (player.getTeam() != null) {
            player.getTeam().getPlayers().remove(player);
        }

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

    public Optional<Team> getTeamByName(String name) {
        return teamRepository.getByName(name);
    }

    public void deleteTeamByName(String name) {
        Optional<Team> team = teamRepository.getByName(name);
        if (team != null) {
            // Find all players associated with this team
            List<Player> players = playerRepository.getAll();
            for (Player player : players) {
                if (player.getTeam() != null && player.getTeam().getId().equals(team.get().getId())) {
                    player.setTeam(null);
                    playerRepository.update(player);
                }
            }
            
            // Now delete the team
            teamRepository.delete(team.get().getId());
        } else {
            throw new IllegalArgumentException("Team not found with name: " + name);
        }
    }
}
