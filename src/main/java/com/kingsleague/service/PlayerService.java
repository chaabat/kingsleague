package com.kingsleague.service;


import com.kingsleague.model.Player;
import com.kingsleague.repository.interfaces.PlayerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class PlayerService {
    private PlayerRepository playerRepository;

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getPlayerById(Long id) {
        return playerRepository.get(id);
    }

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return playerRepository.getAll();
    }

    public Player getPlayerByUsername(String username) {
        return playerRepository.getByUsername(username);
    }

    public void addPlayer(Player player) {
        playerRepository.add(player);
    }

    public void updatePlayer(Player player) {
        playerRepository.update(player);
    }
    public void deletePlayer(Long id) {
        playerRepository.delete(id);
    }
    public void deletePlayerByUsername(String username) {
        Player player = playerRepository.getByUsername(username);
        if (player != null) {
            deletePlayer(player.getId());
        }
    }
}
