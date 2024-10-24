package com.kingsleague.repository.interfaces;

import com.kingsleague.model.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    void add(Game game);
    void update(Game game);
    void delete(Long id);
    List<Game> getAll();
    Optional<Game> get(Long id);
    Optional<Game> getByName(String name);
}
