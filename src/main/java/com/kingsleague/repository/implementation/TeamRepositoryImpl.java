package com.kingsleague.repository.implementation;

import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.TeamRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TeamRepositoryImpl implements TeamRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamRepositoryImpl.class);

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
    public Optional<Team> getByName(String name) {
        LOGGER.info("Finding team with name: {}", name);
        TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t WHERE t.name = :name", Team.class);
        query.setParameter("name", name);
        try {
            Team team = query.getSingleResult();
            return Optional.of(team);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Team> get(Long id) {
        LOGGER.info("Finding team with id: {}", id);
        Team team = entityManager.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    @Override
    public List<Team> getAll() {
        LOGGER.info("Finding all teams");
        TypedQuery<Team> query = entityManager.createQuery("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players", Team.class);
        return query.getResultList();
    }

    @Override
    public void add(Team team) {
        LOGGER.info("Saving team: {}", team.getName());
        entityManager.persist(team);
    }

    @Override
    public void update(Team team) {
        LOGGER.info("Updating team: {}", team.getName());
        entityManager.merge(team);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting team with id: {}", id);
        get(id).ifPresent(team -> entityManager.remove(team));
    }
}
