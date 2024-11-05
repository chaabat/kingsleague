package com.kingsleague.repository.interfaces;

import java.util.List;

import com.kingsleague.model.Avertissement;
import com.kingsleague.model.Team;
 

public interface AvertissementRepository {

    
 void add(Avertissement avertissement);

 List<Team> getEquipeAvecAver();

 List<Team> getEquipeSansAver();

 List<Avertissement> findAll();





}
