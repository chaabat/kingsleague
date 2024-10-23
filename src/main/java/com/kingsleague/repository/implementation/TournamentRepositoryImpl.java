package com.kingsleague.repository.implementation;

import com.kingsleague.model.Tournament;
import com.kingsleague.repository.interfaces.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TournamentRepositoryImpl implements TournamentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

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

    }

    @Override
    public void update(Tournament tournament) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Tournament> getAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Tournament> get(Long id) {
        return Optional.empty();
    }
}
