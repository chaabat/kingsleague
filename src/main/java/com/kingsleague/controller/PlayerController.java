package com.kingsleague.controller;

import com.kingsleague.model.Player;
import com.kingsleague.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PlayerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    private PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public List<Player> getAllPlayers(Player player){
        return playerService.getAllPlayers();
    }
    public Player getPlayerByUsername(String username){
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
