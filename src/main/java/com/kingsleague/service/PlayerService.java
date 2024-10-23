package com.kingsleague.service;


import com.kingsleague.repository.interfaces.PlayerRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PlayerService {
    private PlayerRepository playerRepository;

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
}
