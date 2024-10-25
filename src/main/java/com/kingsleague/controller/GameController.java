package com.kingsleague.controller;

import com.kingsleague.model.Game;
import com.kingsleague.model.Team;
import com.kingsleague.service.GameService;
import com.kingsleague.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class GameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    private GameService gameService;
    private TeamService teamService;

    // Constructor
    public GameController(GameService gameService, TeamService teamService) {
        this.gameService = gameService;
        this.teamService = teamService;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
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
        LOGGER.info("Attempting to add team '{}' to game '{}'", teamName, gameName);

        try {
            gameService.addTeamToGame(gameName, teamName);
            LOGGER.info("Team successfully added to game");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error adding team to game: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error occurred while adding team to game", e);
            throw new RuntimeException("Failed to add team to game: " + e.getMessage(), e);
        }
    }
    public void removeTeamFromGame(String gameName, String teamName) {
        gameService.removeTeamFromGame(gameName, teamName);
    }
}
