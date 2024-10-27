package com.kingsleague.repository.implementation;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingsleague.model.Game;
import com.kingsleague.model.Tournament;
import com.kingsleague.repository.interfaces.GameRepository;

public class GameRepositoryImpl implements GameRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRepositoryImpl.class);

    private EntityManager entityManager;

    private static GameRepositoryImpl instance;

    private GameRepositoryImpl() {
        // Private constructor to prevent instantiation
    }

    public static synchronized GameRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new GameRepositoryImpl();
        }
        return instance;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Game> get(Long id) {
        LOGGER.info("Finding game with id: {}", id);
        Game game = entityManager.find(Game.class, id);
        return Optional.ofNullable(game);
    }

    @Override
    public List<Game> getAll() {
        LOGGER.info("Finding all games");
        TypedQuery<Game> query = entityManager.createQuery("SELECT DISTINCT g FROM Game g", Game.class);
        return query.getResultList();
    }

    @Override
    public void add(Game game) {
        LOGGER.info("Saving game: {}", game.getName());
        entityManager.persist(game);
    }

    @Override
    public void update(Game game) {
        LOGGER.info("Updating game: {}", game.getName());
        entityManager.merge(game);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting game with id: {}", id);
        get(id).ifPresent(game -> entityManager.remove(game));
    }

    @Override
    public Optional getByTitle(String title) {
        LOGGER.info("Finding tournament with title: {}", title);
        TypedQuery<Tournament> query = entityManager.createQuery(
            "SELECT DISTINCT t FROM Tournament t LEFT JOIN FETCH t.teams LEFT JOIN FETCH t.game WHERE t.title = :title", Tournament.class);
        query.setParameter("title", title);
        List<Tournament> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

}
