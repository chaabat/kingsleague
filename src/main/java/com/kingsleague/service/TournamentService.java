package com.kingsleague.service;

import com.kingsleague.model.Team;
import com.kingsleague.model.Tournament;
import com.kingsleague.model.enums.TournamentStatut;
import com.kingsleague.repository.interfaces.TeamRepository;
import com.kingsleague.repository.interfaces.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class TournamentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentService.class);

    private TournamentRepository tournamentRepository;
    private TeamRepository teamRepository;

    public void setTournamentRepository(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Tournament> getTournamentById(Long id) {
        return Optional.ofNullable(tournamentRepository.get(id)).orElseThrow(()
                ->new IllegalArgumentException("Tournament not found"));
    }

    public Optional<Tournament> getTournamentByName(String name) {
        return tournamentRepository.getByName(name);
    }

    @Transactional(readOnly = true)
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.getAll();
    }

    public void addTournament(Tournament tournament) {
        tournamentRepository.add(tournament);
    }

    public void updateTournament(Tournament tournament) {
        tournamentRepository.update(tournament);
    }

    public void deleteTournament(Long id) {
        tournamentRepository.delete(id);
    }

    public void deleteTournamentByName(String name) {
        LOGGER.info("Deleting tournament with name: {}", name);
        Optional<Tournament> tournamentOptional = tournamentRepository.getByName(name);
        if (tournamentOptional.isPresent()) {
            Tournament tournament = tournamentOptional.get();
            tournamentRepository.delete(tournament.getId());
            LOGGER.info("Tournament deleted successfully");
        } else {
            LOGGER.warn("Tournament not found: {}", name);
            throw new IllegalArgumentException("Tournament not found: " + name);
        }
    }

    public void changeStatut(String tournamentName , TournamentStatut statut){
        Optional<Tournament> tournament = tournamentRepository.getByName(tournamentName);
        if(tournament.isPresent()){
            tournament.get().setStatut(statut);
            updateTournament(tournament.orElse(null));
        }
    }

    public void cancelTournament(String tournamentName) {
        changeStatut(tournamentName,TournamentStatut.CANCELED);

    }



    public void updateTournamentStatut() {
        LOGGER.info("Updating tournament statuses");
        List<Tournament> tournaments = getAllTournaments();
        for (Tournament tournament : tournaments) {
            if (tournament.getStatut() == TournamentStatut.SCHEDULED && tournament.getDateStart().before(new java.util.Date())) {
                tournament.setStatut(TournamentStatut.IN_PROGRESS);
                updateTournament(tournament);
            } else if (tournament.getStatut() == TournamentStatut.IN_PROGRESS && tournament.getEndDate().before(new java.util.Date())) {
                tournament.setStatut(TournamentStatut.COMPLETED);
                updateTournament(tournament);
            }
        }
    }

    public void addTeamToTournament(String tournamentName, String teamName) {
        LOGGER.info("Adding team {} to tournament {}", teamName, tournamentName);

        // Get the tournament using Optional
        Optional<Tournament> tournamentOptional = tournamentRepository.getByName(tournamentName);
        Tournament tournament = tournamentOptional.orElseThrow(() ->
                new IllegalArgumentException("Tournament not found with title: " + tournamentName));

        // Get the team using Optional
        Optional<Team> teamOptional = teamRepository.getByName(teamName);
        Team team = teamOptional.orElseThrow(() ->
                new IllegalArgumentException("Team not found with name: " + teamName));

        // Add the team to the tournament and vice versa
        tournament.getTeams().add(team);
        team.getTournaments().add(tournament);

        // Update the tournament and team
        tournamentRepository.update(tournament);
        teamRepository.update(team);
    }

    public void removeTeamFromTournament(String tournamentName, String teamName) {
        LOGGER.info("Removing team {} from tournament {}", teamName, tournamentName);

        // Get the tournament using Optional
        Optional<Tournament> tournamentOptional = tournamentRepository.getByName(tournamentName);
        Tournament tournament = tournamentOptional.orElseThrow(() ->
                new IllegalArgumentException("Tournament not found with title: " + tournamentName));

        // Get the team using Optional
        Optional<Team> teamOptional = teamRepository.getByName(teamName);
        Team team = teamOptional.orElseThrow(() ->
                new IllegalArgumentException("Team not found with name: " + teamName));

        // Attempt to remove the team from the tournament
        if (tournament.getTeams().remove(team)) {
            team.getTournaments().remove(tournament);
            tournamentRepository.update(tournament);
            teamRepository.update(team);
        } else {
            LOGGER.warn("Team {} was not part of tournament {}", teamName, tournamentName);
        }
    }


}
