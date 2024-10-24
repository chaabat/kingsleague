package com.kingsleague.service;

import com.kingsleague.model.Game;
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


}
