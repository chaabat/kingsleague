package com.kingsleague.repository.implementation;

import com.kingsleague.model.Game;
import com.kingsleague.repository.interfaces.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;


public class GameRepositoryImpl implements GameRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameRepositoryImpl.class);

    private static final String LIST = "SELECT DISTINCT g FROM Game g LEFT JOIN FETCH g.tournaments t LEFT JOIN FETCH t.teams";

    private static final String GET_NAME = "SELECT g FROM Game g WHERE g.name = :name";

    private static GameRepositoryImpl instance;
    private EntityManager entityManager;

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
    public void add(Game game) {
        LOGGER.info("Adding game: {}", game.getName());
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
Optional<Game> game = get(id);
if (game.isPresent()) {
    entityManager.remove(game);
}
    }

    @Override
    public List<Game> getAll() {
        LOGGER.info("List all games");
        TypedQuery<Game> query = entityManager.createQuery(LIST, Game.class);
        return query.getResultList();
    }

    @Override
    public  Optional<Game> get(Long id) {
        LOGGER.info("Finding game with id: {}", id);
       return Optional.ofNullable(entityManager.find(Game.class, id));
    }

    @Override
    public Optional<Game> getByName(String name) {
        LOGGER.info("Finding game with name: {}", name);
        TypedQuery<Game> query = entityManager.createQuery(GET_NAME, Game.class);
        query.setParameter("name", name);
        List<Game> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }



}
