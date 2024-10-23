package com.kingsleague.controller;

import com.kingsleague.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    private PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }
}
