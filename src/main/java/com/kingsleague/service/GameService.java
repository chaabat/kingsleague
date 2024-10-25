package com.kingsleague.service;

import com.kingsleague.model.Game;
import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.GameRepository;
import com.kingsleague.repository.interfaces.TeamRepository;
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

    public GameService(GameRepository gameRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
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
        Optional<Game> gameOptional = gameRepository.getByName(name);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            // Initialize the teams collection
            game.getTeams().size(); // This will force initialization
        }
        return gameOptional;
    }

    public void addGame(Game game) {
        gameRepository.add(game);
    }

    public void updateGame(Game game) {
        gameRepository.update(game);
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

        LOGGER.info("Updating game in repository");
        gameRepository.update(game);
        LOGGER.info("Updating team in repository");
        teamRepository.update(team);
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


}






