package com.kingsleague.controller;

import com.kingsleague.model.Game;
 
import com.kingsleague.service.GameService;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class GameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private GameService gameService;

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public List<Game> getAllGames() {
        LOGGER.info("Getting all games");
        return gameService.getAll();
    }

    public Optional<Game> getGameByTitle(String title) {
        LOGGER.info("Getting game by name: {}", title);
        return gameService.getGameByTitle(title);
    }

    public void createGame(Game game) {
        LOGGER.info("Creating new game: {}", game.getName());
        gameService.add(game);
    }

    public void updateGame(Game game) {
        LOGGER.info("Updating game: {}", game.getName());
        gameService.update(game);
    }

    public void deleteGameByName(String name) {
        LOGGER.info("Deleting game by name: {}", name);
        gameService.deleteGameByName(name);
    }
}
