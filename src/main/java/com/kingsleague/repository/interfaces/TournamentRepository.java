package com.kingsleague.repository.interfaces;


import com.kingsleague.model.Team;
import com.kingsleague.model.Tournament;

import java.util.List;
import java.util.Optional;


public interface TournamentRepository {

    void add(Tournament tournament);
    void update(Tournament tournament);
    void delete(Long id);
    List<Tournament> getAll();
    Optional<Tournament>  get(Long id);
    Optional<Tournament>  getByName(String name);
    int calculateEstimatedDuration(Long tournamentId);
}
