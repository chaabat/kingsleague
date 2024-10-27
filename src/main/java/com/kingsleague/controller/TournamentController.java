package com.kingsleague.controller;

import com.kingsleague.model.Tournament;
import com.kingsleague.model.enums.TournamentStatut;
import com.kingsleague.service.TournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TournamentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);

    private TournamentService tournamentService;

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public List<Tournament> getAllTournaments() {
        LOGGER.info("Getting all tournaments");
        return tournamentService.getAll();
    }

    public Optional<Tournament> getTournamentByTitle(String title) {
        LOGGER.info("Getting tournament by title: {}", title);
        return tournamentService.getTournamentByTitle(title);
    }

    public void createTournament(Tournament tournament) {
        LOGGER.info("Creating new tournament: {}", tournament.getTitle());
        tournamentService.add(tournament);
    }

    public void updateTournament(Tournament tournament) {
        LOGGER.info("Updating tournament: {}", tournament.getTitle());
        tournamentService.update(tournament);
    }

    public void deleteTournamentByTitle(String title) {
        LOGGER.info("Deleting tournament by title: {}", title);
        tournamentService.deleteTournamentByTitle(title);
    }

    public void addTeamToTournament(String tournamentTitle, String teamName) {
        LOGGER.info("Adding team {} to tournament {}", teamName, tournamentTitle);
        tournamentService.addTeamToTournament(tournamentTitle, teamName);
    }

    public void removeTeamFromTournament(String tournamentTitle, String teamName) {
        LOGGER.info("Removing team {} from tournament {}", teamName, tournamentTitle);
        tournamentService.removeTeamFromTournament(tournamentTitle, teamName);
    }

    public void changeStatus(String tournamentTitle, TournamentStatut newStatut) {
        LOGGER.info("Changing status of tournament {} to {}", tournamentTitle, newStatut);
        tournamentService.changeStatus(tournamentTitle, newStatut);
    }

    public void cancelTournament(String tournamentTitle) {
        LOGGER.info("Cancelling tournament with title: {}", tournamentTitle);
        tournamentService.cancelTournament(tournamentTitle);
    }

    public void createTournamentWithGame(String title, String gameName, int difficulty, int averageMatchDuration, List<String> teamNames) {
        LOGGER.info("Creating new tournament with game and teams: {}", title);
        tournamentService.createTournamentWithGame(title, gameName, difficulty, averageMatchDuration, teamNames);
    }
    public void addGameToTournament(String tournamentTitle, String gameName) {
        LOGGER.info("Adding game {} to tournament {}", gameName, tournamentTitle);
        tournamentService.addGameToTournament(tournamentTitle, gameName);
    }
    
    
    public int calculateEstimatedDuration(String title) {
        LOGGER.info("Calculating estimated duration for tournament: {}", title);
        return tournamentService.calculateEstimatedDuration(title);
    }
}