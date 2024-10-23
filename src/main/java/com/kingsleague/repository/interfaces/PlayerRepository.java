package com.kingsleague.repository.interfaces;


import com.kingsleague.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    void add(Player player);
    void update(Player player);
    void delete(Long id);
    List<Player> getAll();
     Player get(Long id);
    Player getByUsername(String username);
}
