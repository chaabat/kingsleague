package com.kingsleague.service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import com.kingsleague.repository.interfaces.AvertissementRepository;
import com.kingsleague.model.Avertissement;
import com.kingsleague.model.Team;

@Transactional
public class AvertissementService {
    private AvertissementRepository avertissementRepository;

    public void setAvertissementRepository(AvertissementRepository avertissementRepository) {
        this.avertissementRepository = avertissementRepository;
    }

    @Transactional
    public void add(Avertissement avertissement) {
        avertissementRepository.add(avertissement);
    }

    public List<Avertissement> getAllAvertissements() {
        return avertissementRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    public List<Team> getEquipeAvecAver() {
        return avertissementRepository.findAll().stream()
                .filter(av -> av.getCouleur() != null && !av.getCouleur().isEmpty())
                .map(Avertissement::getTeam)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Team> getEquipeSansAver() {
        return avertissementRepository.getEquipeSansAver();
    }
}
