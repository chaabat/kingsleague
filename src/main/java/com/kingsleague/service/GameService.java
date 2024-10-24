package com.kingsleague.service;

import com.kingsleague.model.Game;
import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.GameRepository;
import com.kingsleague.repository.interfaces.TeamRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class GameService {


    private GameRepository gameRepository;
    private TeamRepository teamRepository;

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Game getGame(Long id) {
        return gameRepository.get(id);
    }

    @Transactional(readOnly = true)
    public List<Game> getAllGames() {
        return gameRepository.getAll();
    }

    public Game getGameByName(String name){
        return gameRepository.getByName(name);
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
        Game game = gameRepository.getByName(name);
        if (game != null) {
            deleteGame(game.getId());
        }
    }

    public void addTeamToGame(String gameName, String teamName){
        Game game = gameRepository.getByName(gameName);
        if (game == null) {
            throw new IllegalArgumentException("Game not found with name: " + gameName);
        }

        Team team = teamRepository.getByName(teamName);
        if (team == null) {
            throw new IllegalArgumentException("Team not found with name: " + teamName);
        }

        game.getTeams().add(team);
        team.getGames().add(game);
        gameRepository.update(game);
        teamRepository.update(team);
    }

    public void removeTeamFromGame(String gameName, String teamName) {
        Game game = gameRepository.getByName(gameName);
        if (game == null) {
            throw new IllegalArgumentException("Game not found with name: " + gameName);
        }

        Team team = teamRepository.getByName(teamName);
        if (team == null) {
            throw new IllegalArgumentException("Team not found with name: " + teamName);
        }

        if (game.getTeams().remove(team)) {
            team.getGames().remove(game);
            gameRepository.update(game);
            teamRepository.update(team);
        }
    }


}
