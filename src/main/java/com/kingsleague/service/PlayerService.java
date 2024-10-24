package com.kingsleague.service;

import com.kingsleague.model.Player;
import com.kingsleague.repository.interfaces.PlayerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class PlayerService {
    private PlayerRepository playerRepository;

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.get(id);
    }

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return playerRepository.getAll();
    }

    public Optional<Player> getPlayerByUsername(String username) {
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
        Optional<Player> optionalPlayer = playerRepository.getByUsername(username);
        optionalPlayer.ifPresent(player -> deletePlayer(player.getId()));
    }
}
