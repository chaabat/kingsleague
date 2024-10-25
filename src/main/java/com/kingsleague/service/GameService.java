package com.kingsleague.service;

import com.kingsleague.model.Game;
import com.kingsleague.model.Team;
import com.kingsleague.model.Tournament;
import com.kingsleague.repository.interfaces.GameRepository;
import com.kingsleague.repository.interfaces.TeamRepository;
import com.kingsleague.repository.interfaces.TournamentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Transactional
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private GameRepository gameRepository;
    private TeamRepository teamRepository;
    private TournamentRepository tournamentRepository;

    public GameService(GameRepository gameRepository, TeamRepository teamRepository, TournamentRepository tournamentRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Game> getGame(Long id) {
        return gameRepository.get(id);
    }

    @Transactional(readOnly = true)
    public List<Game> getAllGames() {
        return gameRepository.getAll();
    }

    public Optional<Game> getGameByName(String name) {
        LOGGER.info("Fetching game with name: {}", name);
        return gameRepository.getByName(name);
    }

    public void addGame(Game game) {
        gameRepository.add(game);
    }

    public void updateGame(Game game) {
        LOGGER.info("Updating game: {}", game.getName());
        gameRepository.add(game);
    }

    public void deleteGame(Long id) {
        gameRepository.delete(id);
    }

    public void deleteGameByName(String name) {
        Optional<Game> gameOptional = gameRepository.getByName(name);
        Game game = gameOptional.orElseThrow(() ->
                new IllegalArgumentException("Game not found with name: " + name)
        );
        deleteGame(game.getId());
    }

    public void addTeamToGame(String gameName, String teamName) {
        LOGGER.info("Adding team '{}' to game '{}'", teamName, gameName);
        Game game = getGameByName(gameName)
            .orElseThrow(() -> new IllegalArgumentException("Game not found: " + gameName));
        Team team = teamRepository.getByName(teamName)
            .orElseThrow(() -> new IllegalArgumentException("Team not found: " + teamName));

        game.getTeams().add(team);
        team.getGames().add(game);

        LOGGER.info("Updating game and team in repository");
        gameRepository.add(game);
        teamRepository.add(team);
        LOGGER.info("Team successfully added to game");
    }

    public void removeTeamFromGame(String gameName, String teamName) {
        Optional<Game> gameOptional = gameRepository.getByName(gameName);
        Game game = gameOptional.orElseThrow(() ->
                new IllegalArgumentException("Game not found with name: " + gameName)
        );

        // Assuming teamRepository.getByName returns Optional<Team>
        Team team = teamRepository.getByName(teamName)
                .orElseThrow(() ->
                        new IllegalArgumentException("Team not found with name: " + teamName)
                );

        if (game.getTeams().remove(team)) {
            team.getGames().remove(game);
            gameRepository.update(game);
            teamRepository.update(team);
        } else {
            throw new IllegalArgumentException("Team is not part of this game");
        }
    }

    public void addTournamentToGame(String gameName, String tournamentName) {
        LOGGER.info("Adding tournament '{}' to game '{}'", tournamentName, gameName);
        Game game = getGameByName(gameName)
            .orElseThrow(() -> new IllegalArgumentException("Game not found: " + gameName));
        Tournament tournament = tournamentRepository.getByName(tournamentName)
            .orElseThrow(() -> new IllegalArgumentException("Tournament not found: " + tournamentName));

        game.getTournaments().add(tournament);
        tournament.setGame(game);

        LOGGER.info("Updating game and tournament in repository");
        gameRepository.add(game);
        LOGGER.info("Tournament successfully added to game");
    }

}






