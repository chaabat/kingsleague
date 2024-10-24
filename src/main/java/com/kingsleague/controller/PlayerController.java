package com.kingsleague.controller;

import com.kingsleague.model.Player;
import com.kingsleague.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class PlayerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    private PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }
    public Optional<Player> getPlayerByUsername(String username){
        return playerService.getPlayerByUsername(username);
    }
    public void addPlayer(Player player){
        playerService.addPlayer(player);
    }
    public void updatePlayer(Player player){
        playerService.updatePlayer(player);
    }
    public void deletePlayerByUsername(String username){
        playerService.deletePlayerByUsername(username);
    }
}
