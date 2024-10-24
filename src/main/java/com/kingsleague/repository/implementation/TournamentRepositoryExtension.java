package com.kingsleague.repository.implementation;

import com.kingsleague.model.Game;
import com.kingsleague.model.Tournament;
import com.kingsleague.repository.interfaces.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


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
    public Optional<Tournament> get(Long id) {
     return Optional.ofNullable(baseRepository.get(id).get());
    }

    @Override
    public Optional<Tournament> getByName(String name) {
       return Optional.ofNullable(baseRepository.getByName(name).get());
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        LOGGER.info("Calculating advanced estimated duration for tournament with id: {}", tournamentId);

        Optional<Tournament> tournament = get(tournamentId);
        if (!tournament.isPresent() || tournament.get().getTeams() == null || tournament.get().getTeams().isEmpty() || tournament.get().getGame() == null) {
            LOGGER.warn("Tournament, teams, or game not found for id: {}", tournamentId);
            return 0;
        }

        int numberOfTeams = tournament.get().getTeams().size();

        // Assuming all games have the same average duration and difficulty, or use the first game
        Game firstGame = tournament.get().getGame();
        int averageMatchDuration = firstGame.getDurationAverageMatch();
        int gameDifficulty = firstGame.getDifficulty();

        // Calculate the estimated duration with break and ceremony times
        int estimatedDuration = (numberOfTeams * averageMatchDuration * gameDifficulty)
                + tournament.get().getTimePause()
                + tournament.get().getTimeCeremony();

        LOGGER.info("Estimated duration for tournament with id {}: {} minutes", tournamentId, estimatedDuration);

        return estimatedDuration;
    }



}
