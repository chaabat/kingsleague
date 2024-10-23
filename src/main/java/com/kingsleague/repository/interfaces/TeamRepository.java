package com.kingsleague.repository.interfaces;


import com.kingsleague.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {

    void add(Team team);
    void update(Team team);
    void delete(Long id);
    List<Team> getAll();
    Team get(Long id);
    Team getByName(String name);
}
