package com.kingsleague.repository.implementation;

import com.kingsleague.model.Player;
import com.kingsleague.repository.interfaces.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    private static PlayerRepositoryImpl instance;
    private EntityManager entityManager;

    private PlayerRepositoryImpl() {
        // Private constructor to prevent instantiation
    }

    public static synchronized PlayerRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new PlayerRepositoryImpl();
        }
        return instance;
    }
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Player player) {

    }

    @Override
    public void update(Player player) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Player> getAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Player> get(Long id) {
        return Optional.empty();
    }
}
