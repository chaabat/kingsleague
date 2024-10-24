package com.kingsleague.service;


import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.PlayerRepository;
import com.kingsleague.repository.interfaces.TeamRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Team getTeamById(Long id) {
        return teamRepository.get(id);
    }

    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        return teamRepository.getAll();
    }

    public Team getTeamByName(String name) {
        return teamRepository.getByName(name);
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
}
