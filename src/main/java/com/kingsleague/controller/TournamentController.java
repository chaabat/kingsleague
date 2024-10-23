package com.kingsleague.controller;

import com.kingsleague.service.TournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TournamentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);

    private TournamentService tournamentService;

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }
}
