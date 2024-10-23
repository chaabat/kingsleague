package com.kingsleague.repository.implementation;

import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TeamRepositoryImpl implements TeamRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    private static TeamRepositoryImpl instance;
    private EntityManager entityManager;

    private TeamRepositoryImpl() {
        // Private constructor to prevent instantiation
    }

    public static synchronized TeamRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new TeamRepositoryImpl();
        }
        return instance;
    }
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Team team) {

    }

    @Override
    public void update(Team team) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Team> getAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Team> get(Long id) {
        return Optional.empty();
    }
}
