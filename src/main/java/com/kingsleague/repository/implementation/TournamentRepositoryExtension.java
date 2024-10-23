package com.kingsleague.repository.implementation;

import com.kingsleague.model.Game;
import com.kingsleague.model.Tournament;
import com.kingsleague.repository.interfaces.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;


public class TournamentRepositoryExtension implements TournamentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRepositoryExtension.class);

    private TournamentRepositoryImpl baseRepository;

    public TournamentRepositoryExtension(TournamentRepositoryImpl baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public void add(Tournament tournament) {
        baseRepository.add(tournament);    }

    @Override
    public void update(Tournament tournament) {
       baseRepository.update(tournament);
    }

    @Override
    public void delete(Long id) {
  baseRepository.delete(id);
    }

    @Override
    public List<Tournament> getAll() {
        return baseRepository.getAll();
    }

    @Override
    public Tournament get(Long id) {
     return   baseRepository.get(id);
    }

    @Override
    public Tournament getByName(String name) {
       return  baseRepository.getByName(name);
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        LOGGER.info("Calculating advanced estimated duration for tournament with id: {}", tournamentId);

        Tournament tournament = get(tournamentId);
        if (tournament == null || tournament.getTeams() == null || tournament.getGames() == null || tournament.getGames().isEmpty()) {
            LOGGER.warn("Tournament, teams, or games not found for id: {}", tournamentId);
            return 0;
        }

        int numberOfTeams = tournament.getTeams().size();

        // Assuming all games have the same average duration and difficulty, or use the first game
        Game firstGame = tournament.getGames().get(0);
        int averageMatchDuration = firstGame.getDurationAverageMatch();
        int gameDifficulty = firstGame.getDifficulty();

        // Calculate the estimated duration with break and ceremony times
        int estimatedDuration = (numberOfTeams * averageMatchDuration * gameDifficulty)
                + tournament.getTimePause()
                + tournament.getTimeCeremony();

        LOGGER.info("Estimated duration for tournament with id {}: {} minutes", tournamentId, estimatedDuration);

        return estimatedDuration;
    }


}
