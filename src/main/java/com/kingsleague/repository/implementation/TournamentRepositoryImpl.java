package com.kingsleague.repository.implementation;

import com.kingsleague.model.Tournament;
import com.kingsleague.repository.interfaces.TournamentRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TournamentRepositoryImpl implements TournamentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRepositoryImpl.class);

    private static TournamentRepositoryImpl instance;
    private EntityManager entityManager;

    protected TournamentRepositoryImpl() {
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
    public Optional<Tournament> get(Long id) {
        LOGGER.info("Finding tournament with id: {}", id);
        Tournament tournament = entityManager.find(Tournament.class, id);
        return Optional.ofNullable(tournament);
    }

    @Override
    public List<Tournament> getAll() {
        LOGGER.info("Finding all tournaments");
        TypedQuery<Tournament> query = entityManager.createQuery("SELECT DISTINCT t FROM Tournament t LEFT JOIN FETCH t.teams", Tournament.class);
        return query.getResultList();
    }

    @Override
    public void add(Tournament tournament) {
        LOGGER.info("Saving tournament: {}", tournament.getTitle());
        entityManager.persist(tournament);
        entityManager.flush();
    }
    
    @Override
    public void update(Tournament tournament) {
        LOGGER.info("Updating tournament: {}", tournament.getTitle());
        entityManager.merge(tournament);
        entityManager.flush();
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting tournament with id: {}", id);
        get(id).ifPresent(tournament -> entityManager.remove(tournament));
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        Optional<Tournament> tournamentOpt = get(tournamentId);
        return tournamentOpt.map(tournament -> {
            if (tournament.getGame() == null) {
                return 0;
            }
            int numberOfTeams = tournament.getTeams().size();
            int averageMatchDuration = tournament.getGame().getAverageMatchDuration();
            return (numberOfTeams * averageMatchDuration) + tournament.getMatchBreakTime();
        }).orElse(0);
    }

    @Override
    public Optional<Tournament> getByName(String title) {
        LOGGER.info("Finding tournament with title: {}", title);
        TypedQuery<Tournament> query = entityManager.createQuery(
            "SELECT DISTINCT t FROM Tournament t LEFT JOIN FETCH t.teams LEFT JOIN FETCH t.game WHERE t.title = :title", Tournament.class);
        query.setParameter("title", title);
        try {
            Tournament tournament = query.getSingleResult();
            return Optional.of(tournament);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
