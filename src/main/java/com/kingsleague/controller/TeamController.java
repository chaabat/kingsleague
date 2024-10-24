package com.kingsleague.controller;

import com.kingsleague.model.Player;
import com.kingsleague.model.Team;
import com.kingsleague.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TeamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    private TeamService teamService;

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }
    public Optional<Team> getTeamByName(String name) {
        return teamService.getTeamByName(name);
    }
    public void addTeam(Team team) {
        teamService.addTeam(team);
    }
    public void updateTeam(Team team) {
        teamService.updateTeam(team);
    }
    public void deleteTeamByName(String name ) {
        teamService.deleteTeamByName(name);
    }

    public void addPlayerToTeam(String playerName, String playerUsername) {
        teamService.addPlayerToTeam(playerName, playerUsername);
    }

    public void removePlayerFromTeam(String playerName, String playerUsername) {
        teamService.removePlayerFromTeam(playerName, playerUsername);
    }
}
