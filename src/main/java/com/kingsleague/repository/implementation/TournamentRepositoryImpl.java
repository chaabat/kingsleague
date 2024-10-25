package com.kingsleague.repository.implementation;

import com.kingsleague.model.Tournament;
import com.kingsleague.repository.interfaces.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
        LOGGER.info("Deleting tournament with id: {}", id);
        Tournament tournament = entityManager.find(Tournament.class, id);
        if (tournament != null) {
            entityManager.remove(tournament);
        } else {
            LOGGER.warn("Tournament not found with id: {}", id);
        }
    }

    @Override
    public List<Tournament> getAll() {
        LOGGER.info("List All Tournaments");
        TypedQuery<Tournament> query = entityManager.createQuery(LIST, Tournament.class);
        return query.getResultList();
    }

    @Override
    public Optional<Tournament> get(Long id) {
        LOGGER.info("Finding tournament with id: {}", id);
        return Optional.ofNullable(entityManager.find(Tournament.class, id));
    }

    @Override
    public Optional<Tournament> getByName(String name) {
        LOGGER.info("Finding tournament with name: {}", name);
        TypedQuery<Tournament> query = entityManager.createQuery( GET_NAME, Tournament.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        LOGGER.info("Calculating estimated duration for tournament with id: {}", tournamentId);

        Optional<Tournament> tournamentOptional = get(tournamentId);

        // Check if the tournament is present using isPresent()
        if (!tournamentOptional.isPresent() || tournamentOptional.get().getTeams() == null ||
                tournamentOptional.get().getTeams().isEmpty() || tournamentOptional.get().getGame() == null) {
            LOGGER.warn("Tournament, teams, or game not found for id: {}", tournamentId);
            return 0;
        }

        // Now we can safely retrieve the tournament
        Tournament tournament = tournamentOptional.get();

        // Get the number of teams
        int numberOfTeams = tournament.getTeams().size();
        int averageMatchDuration = tournament.getGame().getDurationAverageMatch();  // Assuming this returns the average match duration

        // Include the break time (timePause) and calculate estimated duration
        int estimatedDuration = (numberOfTeams * averageMatchDuration) + tournament.getTimePause() + tournament.getTimeCeremony();

        LOGGER.info("Estimated duration for tournament with id {}: {} minutes", tournamentId, estimatedDuration);
        return estimatedDuration;
    }




}
