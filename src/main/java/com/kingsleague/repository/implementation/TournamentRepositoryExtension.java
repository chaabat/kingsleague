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
    public Optional<Tournament> get(Long id) {
        return baseRepository.get(id);
    }

    @Override
    public List<Tournament> getAll() {
        return baseRepository.getAll();
    }

    @Override
    public void add(Tournament tournament) {
        baseRepository.add(tournament);
    }

    @Override
    public void update(Tournament tournament) {
        baseRepository.update(tournament);
    }

    @Override
    public void delete(Long id) {
        baseRepository.delete(id);
    }

    @Override
    public Optional<Tournament> getByName(String name) {
        return baseRepository.getByName(name);
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        LOGGER.info("Calculating advanced estimated duration for tournament with id: {}", tournamentId);
        return get(tournamentId).map(tournament -> {
            if (tournament.getGame() == null) {
                return 0;
            }
            int numberOfTeams = tournament.getTeams().size();
            int averageMatchDuration = tournament.getGame().getAverageMatchDuration();
            int gameDifficulty = tournament.getGame().getDifficulty();
            return (numberOfTeams * averageMatchDuration * gameDifficulty) + tournament.getMatchBreakTime() + tournament.getCeremonyTime();
        }).orElse(0);
    }
}
