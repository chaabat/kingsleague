package com.kingsleague.service;


import com.kingsleague.repository.interfaces.PlayerRepository;
import com.kingsleague.repository.interfaces.TeamRepository;
import org.springframework.transaction.annotation.Transactional;

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
}
