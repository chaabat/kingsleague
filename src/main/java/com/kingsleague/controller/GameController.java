package com.kingsleague.controller;

import com.kingsleague.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);
    private GameService gameService;
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}
