package com.kingsleague.controller;

import com.kingsleague.model.Team;
import com.kingsleague.model.Tournament;
import com.kingsleague.model.enums.TournamentStatut;
import com.kingsleague.service.TournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TournamentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);

    private TournamentService tournamentService;

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    public Tournament getTournamentByName(String name) {
        return tournamentService.getTournamentByName(name);
    }

    public void addTournament(Tournament tournament) {
        tournamentService.addTournament(tournament);
    }

    public void updateTournament(Tournament tournament) {
        tournamentService.updateTournament(tournament);
    }

    public void deleteTournamentByName(String name) {
        tournamentService.deleteTournamentByName(name);
    }

    public void addTeamToTournament(String tournamentName, String teamName) {
        tournamentService.addTeamToTournament(tournamentName, teamName);
    }

    public void removeTeamFromTournament(String tournamentName, String teamName) {
        tournamentService.removeTeamFromTournament(tournamentName, teamName);
    }

    public void changeStatut(String tournamentName, TournamentStatut statut) {
        tournamentService.changeStatut(tournamentName, statut);
    }

    public void cancelTournament(String tournamentName) {
        tournamentService.cancelTournament(tournamentName);
    }

    public void updateTournamentStatut() {
        tournamentService.updateTournamentStatut();

    }

}
