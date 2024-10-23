package com.kingsleague.repository.interfaces;


import com.kingsleague.model.Tournament;

import java.util.List;


public interface TournamentRepository {

    void add(Tournament tournament);
    void update(Tournament tournament);
    void delete(Long id);
    List<Tournament> getAll();
    Tournament get(Long id);
    Tournament getByName(String name);
}
