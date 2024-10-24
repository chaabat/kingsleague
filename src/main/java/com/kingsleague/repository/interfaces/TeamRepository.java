package com.kingsleague.repository.interfaces;


import com.kingsleague.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {

    void add(Team team);
    void update(Team team);
    void delete(Long id);
    List<Team> getAll();
    Optional<Team> get(Long id);
    Optional<Team> getByName(String name);
}
