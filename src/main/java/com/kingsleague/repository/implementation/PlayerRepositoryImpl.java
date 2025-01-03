package com.kingsleague.repository.implementation;

import com.kingsleague.model.Player;
import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.PlayerRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    private static PlayerRepositoryImpl instance;
    private EntityManager entityManager;

    public PlayerRepositoryImpl() {
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
    public Optional<Player> getByUsername(String username) {
        LOGGER.info("Finding player with username: {}", username);
        TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p WHERE p.username = :username", Player.class);
        query.setParameter("username", username);
        try {
            Player player = query.getSingleResult();
            return Optional.of(player);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }



    @Override
    public Optional<Player> get(Long id) {
        LOGGER.info("Finding player with id: {}", id);
        Player player = entityManager.find(Player.class, id);
        return Optional.ofNullable(player);
    }

    @Override
    public List<Player> getAll() {
        LOGGER.info("Finding all players");
        TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p", Player.class);
        return query.getResultList();
    }

    @Override
    public void add(Player player) {
        LOGGER.info("Saving player: {}", player.getUsername());
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
        get(id).ifPresent(player -> entityManager.remove(player));
    }
}
