package com.kingsleague.repository.implementation;

import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TeamRepositoryImpl implements TeamRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamRepositoryImpl.class);

    private static  final String LIST = "SELECT t FROM Team t" ;
    private static  final String GET_NAME = "SELECT t FROM Team t WHERE t.name = :name " ;

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
        LOGGER.info("Adding team: {}", team.getName());
entityManager.persist(team);
    }

    @Override
    public void update(Team team) {
        LOGGER.info("Updating team: {}", team.getName());
entityManager.merge(team);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting team: {}", id);
Team team = entityManager.find(Team.class, id);
if (team != null) {
    entityManager.remove(team);
}
    }

    @Override
    public List<Team> getAll() {
        LOGGER.info("list all teams");
        TypedQuery<Team> query = entityManager.createQuery(LIST, Team.class);
        return query.getResultList();

    }

    @Override
    public Optional<Team> get(Long id) {
        LOGGER.info("Get team with id: {}", id);
       return Optional.ofNullable(entityManager.find(Team.class, id));
    }

    @Override
    public Optional<Team> getByName(String name) {
        LOGGER.info("Finding team with name: {}", name);
        TypedQuery<Team> query = entityManager.createQuery(GET_NAME, Team.class);
        query.setParameter("name", name);
        List<Team> teams = query.getResultList();
        return teams.isEmpty() ? null : Optional.ofNullable(teams.get(0));
    }
}
