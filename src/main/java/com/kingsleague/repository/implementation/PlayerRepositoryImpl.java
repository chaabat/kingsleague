package com.kingsleague.repository.implementation;

import com.kingsleague.model.Player;
import com.kingsleague.repository.interfaces.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    private static final String LIST = "Select p from Player p";
    private static final String GET_USERNAME = "Select p from Player p WHERE p.username=:username";

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
        LOGGER.info("Adding player: {}", player.getUsername());
entityManager.persist(player);
    }

    @Override
    public void update(Player player) {
        LOGGER.info("Updating player: {}", player.getUsername());
        entityManager.merge(player);

    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting player with id: {}", id);
Player player = entityManager.find(Player.class, id);
if (player != null) {
    entityManager.remove(player);
}
    }

    @Override
    public List<Player> getAll() {
        LOGGER.info("List all players");
        TypedQuery<Player> query = entityManager.createNamedQuery(LIST, Player.class);
        return query.getResultList();
    }

    @Override
    public Player get(Long id) {
        LOGGER.info("Finding player with id: {}", id);
return entityManager.find(Player.class, id);
    }

    @Override
    public Player getByUsername(String username) {
        LOGGER.info("Finding player with Username: {}", username);
        TypedQuery<Player> query = entityManager.createNamedQuery(GET_USERNAME, Player.class);
        query.setParameter("username", username);
        List<Player> players = query.getResultList();
        return players.isEmpty() ? null : players.get(0);
    }
}
