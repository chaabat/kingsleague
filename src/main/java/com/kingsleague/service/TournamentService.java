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

    public Tournament getTournamentById(Long id) {
        return Optional.ofNullable(tournamentRepository.get(id)).orElseThrow(()
                ->new IllegalArgumentException("Tournament not found"));
    }

    public Tournament getTournamentByName(String name) {
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
        Tournament tournament = tournamentRepository.getByName(name);
        if (tournament != null) {
            deleteTournament(tournament.getId());
        }
    }

    public void changeStatut(String tournamentName , TournamentStatut statut){
        Tournament tournament = tournamentRepository.getByName(tournamentName);
        if(tournament != null){
            tournament.setStatut(statut);
            updateTournament(tournament);
        }
    }

    public void cancelTournament(String tournamentName) {
        changeStatut(tournamentName,TournamentStatut.CANCELED);

    }

    public void addTeamToTournament(String tournamentName, String teamName) {
        LOGGER.info("Adding team {} to tournament {}", teamName, tournamentName);
        Tournament tournament = tournamentRepository.getByName(tournamentName) ;
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament not found with title: " + tournamentName);
        }

        Team team = teamRepository.getByName(teamName);
        if (team == null) {
            throw new IllegalArgumentException("Team not found with name: " + teamName);
        }
        tournament.getTeams().add(team);
        team.getTournaments().add(tournament);
        tournamentRepository.update(tournament);
        teamRepository.update(team);
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

    public void removeTeamFromTournament(String tournamentName, String teamName) {
        LOGGER.info("Removing team {} from tournament {}", teamName, tournamentName);
        Tournament tournament = tournamentRepository.getByName(tournamentName);
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament not found with title: " + tournamentName);
        }

        Team team = teamRepository.getByName(teamName);
        if (team == null) {
            throw new IllegalArgumentException("Team not found with name: " + teamName);
        }

        if (tournament.getTeams().remove(team)) {
            team.getTournaments().remove(tournament);
            tournamentRepository.update(tournament);
            teamRepository.update(team);
        } else {
            LOGGER.warn("Team {} was not part of tournament {}", teamName, tournamentName);
        }
    }


}
