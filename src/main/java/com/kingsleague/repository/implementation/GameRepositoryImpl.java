package com.kingsleague.repository.implementation;

import com.kingsleague.model.Game;
import com.kingsleague.repository.interfaces.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GameRepositoryImpl implements GameRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

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

    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Game> getAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Game> get(Long id) {
        return Optional.empty();
    }
}
