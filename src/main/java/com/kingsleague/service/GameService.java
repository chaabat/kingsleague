package com.kingsleague.service;

import com.kingsleague.model.Game;
 
import com.kingsleague.repository.interfaces.GameRepository;
import com.kingsleague.repository.interfaces.TeamRepository;
 
import org.springframework.transaction.annotation.Transactional;
 

import java.util.List;
import java.util.Optional;

@Transactional
public class GameService  {

    private GameRepository gameRepository;
    private TeamRepository teamRepository;

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    public Optional<Game> get(Long id) {
        return gameRepository.get(id);
    }
    @Transactional(readOnly = true)
    public List<Game> getAll() {
        return gameRepository.getAll();
    }
    public void add(Game game) {
        gameRepository.add(game);
    }
    public void update(Game game) {
        gameRepository.update(game);
    }
 
    public void delete(Long id) {
        gameRepository.delete(id);
    }

   
    public Optional<Game> getGameByTitle(String title) {
        return gameRepository.getByTitle(title);
    }

  
    public void deleteGameByName(String title) {
        Optional<Game> game = getGameByTitle(title);
        if (game != null) {
            delete(game.get().getId());
        }
    }
}