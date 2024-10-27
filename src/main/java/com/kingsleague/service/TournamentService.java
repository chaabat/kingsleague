package com.kingsleague.service;

import com.kingsleague.model.Game;
import com.kingsleague.model.Team;
import com.kingsleague.model.Tournament;
import com.kingsleague.model.enums.TournamentStatut;
import com.kingsleague.repository.interfaces.GameRepository;
import com.kingsleague.repository.interfaces.TeamRepository;
import com.kingsleague.repository.interfaces.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

@Transactional
public class TournamentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentService.class);

    private TournamentRepository tournamentRepository;
    private TeamRepository teamRepository;
    private GameRepository gameRepository;

    public void setTournamentRepository(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

  
    public Optional<Tournament> get(Long id) {
        LOGGER.info("Finding tournament with id: {}", id);
        return tournamentRepository.get(id);
    }

  
    @Transactional(readOnly = true)
    public List<Tournament> getAll() {
        LOGGER.info("Finding all tournaments");
        return tournamentRepository.getAll();
    }

  
    public void add(Tournament tournament) {
        LOGGER.info("Saving tournament: {}", tournament.getTitle());
        tournamentRepository.add(tournament);
    }

  
    public void update(Tournament tournament) {
        LOGGER.info("Updating tournament: {}", tournament.getTitle());
        tournamentRepository.update(tournament);
    }

  
    public void delete(Long id) {
        LOGGER.info("Deleting tournament with id: {}", id);
        tournamentRepository.delete(id);
    }

  
    public void addTeamToTournament(String tournamentName, String teamName) {
        LOGGER.info("Adding team {} to tournament {}", teamName, tournamentName);
        Tournament tournament = tournamentRepository.getByName(tournamentName)
            .orElseThrow(() -> new IllegalArgumentException("Tournament not found with title: " + tournamentName));
    
        Team team = teamRepository.getByName(teamName)
            .orElseThrow(() -> new IllegalArgumentException("Team not found with name: " + teamName));
    
        tournament.getTeams().add(team);
        team.getTournaments().add(tournament);

        tournamentRepository.update(tournament);
        teamRepository.update(team);
    
        updateEstimatedDuration(tournament);
    }

    private void updateEstimatedDuration(Tournament tournament) {
        if (tournament.getGame() == null) {
            LOGGER.warn("Unable to calculate duration for tournament '{}'. Game might be missing.", tournament.getTitle());
            return;
        }
        int duration = tournamentRepository.calculateEstimatedDuration(tournament.getId());
        tournament.setEstimatedDuration(duration);
        tournamentRepository.update(tournament);
    }

  
    public void removeTeamFromTournament(String tournamentTitle, String teamName) {
        LOGGER.info("Removing team {} from tournament {}", teamName, tournamentTitle);
        Tournament tournament = tournamentRepository.getByName(tournamentTitle)
            .orElseThrow(() -> new IllegalArgumentException("Tournament not found with title: " + tournamentTitle));

        Team team = teamRepository.getByName(teamName)
            .orElseThrow(() -> new IllegalArgumentException("Team not found with name: " + teamName));

        if (tournament.getTeams().remove(team)) {
            team.getTournaments().remove(tournament);
            tournamentRepository.update(tournament);
            teamRepository.update(team);
        } else {
            LOGGER.warn("Team {} was not part of tournament {}", teamName, tournamentTitle);
        }
    }

  
    public Optional<Tournament> getTournamentByTitle(String title) {
        return tournamentRepository.getByName(title);
    }

  
    public void deleteTournamentByTitle(String title) {
        getTournamentByTitle(title).ifPresent(tournament -> delete(tournament.getId()));
    }

  
    public void changeStatus(String tournamentTitle, TournamentStatut newStatut) {
        getTournamentByTitle(tournamentTitle).ifPresent(tournament -> {
            tournament.setStatus(newStatut);
            update(tournament);
        });
    }

  
    public void cancelTournament(String tournamentTitle) {
        changeStatus(tournamentTitle, TournamentStatut.CANCELED);
    }

  
    public void createTournamentWithGame(String title, String gameName, int difficulty, int averageMatchDuration, List<String> teamNames) {
        Game game = new Game();
        game.setName(gameName);
        game.setDifficulty(difficulty);
        game.setAverageMatchDuration(averageMatchDuration);
        gameRepository.add(game);

        Tournament tournament = new Tournament();
        tournament.setTitle(title);
        tournament.setGame(game);
        tournament.setStatus(TournamentStatut.SCHEDULED);

        Set<Team> teams = new HashSet<>();
        for (String teamName : teamNames) {
            Team team = teamRepository.getByName(teamName)
                .orElseGet(() -> {
                    Team newTeam = new Team();
                    newTeam.setName(teamName);
                    teamRepository.add(newTeam);
                    return newTeam;
                });
            teams.add(team);
            team.getTournaments().add(tournament);
        }
        tournament.setTeams(teams);

        add(tournament);

        // Update teams to establish bidirectional relationship
        for (Team team : teams) {
            teamRepository.update(team);
        }

        updateEstimatedDuration(tournament);
    }

  
    public void addGameToTournament(String tournamentTitle, String gameName) {
        Optional<Tournament> tournamentOptional = tournamentRepository.getByName(tournamentTitle);
        if (!tournamentOptional.isPresent()) {
            throw new IllegalArgumentException("Tournament not found with title: " + tournamentTitle);
        }
        Tournament tournament = tournamentOptional.get();

        Optional<Game> gameOptional = gameRepository.getByTitle(gameName);
        if (!gameOptional.isPresent()) {
            throw new IllegalArgumentException("Game not found with name: " + gameName);
        }
        Game game = gameOptional.get();

        tournament.setGame(game);
        tournamentRepository.update(tournament);
    }

    public int calculateEstimatedDuration(String name) {
        Tournament tournament = tournamentRepository.getByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Tournament not found with name: " + name));
        int duration = tournamentRepository.calculateEstimatedDuration(tournament.getId());
        if (duration == 0) {
            LOGGER.warn("Unable to calculate duration for tournament '{}'. Game might be missing.", name);
            return 0;
        }
        tournament.setEstimatedDuration(duration);
        tournamentRepository.update(tournament);
        return duration;
    }
}
