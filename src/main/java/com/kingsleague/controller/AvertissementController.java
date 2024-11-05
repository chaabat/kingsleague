package com.kingsleague.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingsleague.model.Avertissement;
import com.kingsleague.model.Team;
import com.kingsleague.service.AvertissementService;
import org.springframework.transaction.annotation.Transactional;

public class AvertissementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private AvertissementService advertissementService;
    private String defaultColor;

    public void setAvertissementService(AvertissementService advertissementService) {
        this.advertissementService = advertissementService;
    }

    public void setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
    }

    @Transactional
    public List<Avertissement> getAllAdvertissements() {
        LOGGER.info("Getting all advertissements");
        return advertissementService.getAllAvertissements();
    }

    @Transactional
    public void addAdvertissement(Avertissement avertissement) {
        avertissement.setCouleur(defaultColor);
        LOGGER.info("Adding advertissement: {}", avertissement.getCouleur());
        advertissementService.add(avertissement);
    }

    public List<Team> getEquipeAvecAver() {
        LOGGER.info("Getting teams with warnings");
        return advertissementService.getEquipeAvecAver();
    }

    public List<Team> getEquipeSansAver() {
        LOGGER.info("Getting teams without warnings");
        return advertissementService.getEquipeSansAver();
    }

}
