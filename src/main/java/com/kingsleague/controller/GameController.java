package com.kingsleague.controller;

import com.kingsleague.model.Game;
import com.kingsleague.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class GameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);
    private GameService gameService;
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }
    public Optional<Game>   getGameByName(String name) {
        return gameService.getGameByName(name);
    }
    public void addGame(Game game) {
        gameService.addGame(game);
    }
    public void updateGame(Game game) {
        gameService.updateGame(game);
    }
    public void deleteGameByName(String name) {
        gameService.deleteGameByName(name);
    }

    public void addTeamToGame(String gameName, String teamName) {
        gameService.addTeamToGame(gameName, teamName);
    }
    public void removeTeamFromGame(String gameName, String teamName) {
        gameService.removeTeamFromGame(gameName, teamName);
    }
}
