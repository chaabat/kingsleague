package com.kingsleague.repository.implementation;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingsleague.model.Avertissement;
import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.AvertissementRepository;

public class AvertissementRepositoryImpl implements AvertissementRepository {

        private static final Logger LOGGER = LoggerFactory.getLogger(AvertissementRepositoryImpl.class);

    private EntityManager entityManager;

    private static AvertissementRepositoryImpl instance;

    private AvertissementRepositoryImpl() {
         
    }

    public static synchronized AvertissementRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new AvertissementRepositoryImpl();
        }
        return instance;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Avertissement avertissement) {
        try {
            entityManager.persist(avertissement);
            LOGGER.info("Avertissement added successfully");
        } catch (Exception e) {
            LOGGER.error("Error adding avertissement: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Team> getEquipeAvecAver() {
        try {
            String jpql = "SELECT DISTINCT t FROM Team t INNER JOIN Avertissement a ON t = a.team";
            return entityManager.createQuery(jpql, Team.class).getResultList();
        } catch (Exception e) {
            LOGGER.error("Error retrieving teams with warnings: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Team> getEquipeSansAver() {
        try {
            String jpql = "SELECT t FROM Team t WHERE t NOT IN (SELECT a.team FROM Avertissement a)";
            return entityManager.createQuery(jpql, Team.class).getResultList();
        } catch (Exception e) {
            LOGGER.error("Error retrieving teams without warnings: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Avertissement> findAll() {
        try {
            return entityManager.createQuery("SELECT a FROM Avertissement a", Avertissement.class).getResultList();
        } catch (Exception e) {
            LOGGER.error("Error retrieving all avertissements: {}", e.getMessage());
            throw e;
        }
    }

}
