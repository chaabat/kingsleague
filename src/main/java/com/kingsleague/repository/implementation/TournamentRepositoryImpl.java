package com.kingsleague.repository.implementation;

import com.kingsleague.model.Tournament;
import com.kingsleague.repository.interfaces.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TournamentRepositoryImpl implements TournamentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRepositoryImpl.class);

    private static final String LIST = "SELECT DISTINCT t FROM Tournament t LEFT JOIN FETCH t.teams";
    private static final String GET_NAME = "SELECT t FROM Tournament t WHERE t.name = :name";

    private static TournamentRepositoryImpl instance;
    private EntityManager entityManager;

    private TournamentRepositoryImpl() {
        // Private constructor to prevent instantiation
    }

    public static synchronized TournamentRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new TournamentRepositoryImpl();
        }
        return instance;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Tournament tournament) {
        LOGGER.info("Adding tournament: {}", tournament.getName());
        entityManager.persist(tournament);
    }

    @Override
    public void update(Tournament tournament) {
        LOGGER.info("Updating tournament: {}", tournament.getName());
        entityManager.merge(tournament);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting tournament: {}", id);
        Tournament tournament = get(id);
        if (tournament != null) {
            entityManager.remove(tournament);
        }

    }

    @Override
    public List<Tournament> getAll() {
        LOGGER.info("List All Tournaments");
        TypedQuery<Tournament> query = entityManager.createQuery(LIST, Tournament.class);
        return query.getResultList();
    }

    @Override
    public Tournament get(Long id) {
        LOGGER.info("Finding tournament with id: {}", id);
        return entityManager.find(Tournament.class, id);
    }

    @Override
    public Tournament getByName(String name) {
        LOGGER.info("Finding tournament with name: {}", name);
        TypedQuery<Tournament> query = entityManager.createQuery(GET_NAME, Tournament.class);
        query.setParameter("name", name);
        List<Tournament> tournaments = query.getResultList();
        return tournaments.isEmpty() ? null : tournaments.get(0);
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        LOGGER.info("Calculating estimated duration for tournament with id: {}", tournamentId);

        Tournament tournament = get(tournamentId);
        if (tournament == null || tournament.getGames() == null || tournament.getGames().isEmpty()) {
            LOGGER.warn("Tournament or games not found for id: {}", tournamentId);
            return 0;
        }

        if (tournament.getTeams() == null || tournament.getTeams().isEmpty()) {
            LOGGER.warn("No teams found for tournament with id: {}", tournamentId);
            return 0;
        }


        int numberOfTeams = tournament.getTeams().size();
        int averageMatchDuration = tournament.getGames().get(0).getDurationAverageMatch();  // Assuming all games have the same average match duration

        // Include the break time (timePause)
        int estimatedDuration = (numberOfTeams * averageMatchDuration) + tournament.getTimePause();

        LOGGER.info("Estimated duration for tournament with id {}: {} minutes", tournamentId, estimatedDuration);

        return estimatedDuration;
    }

}
