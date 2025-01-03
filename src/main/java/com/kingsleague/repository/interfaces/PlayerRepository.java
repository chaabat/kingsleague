package com.kingsleague.repository.interfaces;


import com.kingsleague.model.Player;
import com.kingsleague.model.Team;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    void add(Player player);
    void update(Player player);
    void delete(Long id);
    List<Player> getAll();
     Optional<Player> get(Long id);
    Optional<Player>  getByUsername(String username);
     

}
