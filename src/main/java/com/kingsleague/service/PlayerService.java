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

   
    public Optional<Player> get(Long id) {
        return playerRepository.get(id);
    }

      
    @Transactional(readOnly = true)
    public List<Player> getAll() {
        return playerRepository.getAll();
    }
 
    public void add(Player player) {
        playerRepository.add(player);
    }

    
    public void update(Player player) {
        playerRepository.update(player);
    }
 
    public void delete(Long id) {
        playerRepository.delete(id);
    }

   
    public Optional<Player> getPlayerByUsername(String username) {
        return playerRepository.getByUsername(username);
    }
 
    public void deletePlayerByUsername(String username) {
        Optional<Player> player = getPlayerByUsername(username);
        if (player != null) {
            delete(player.get().getId());
        }
    }
}
