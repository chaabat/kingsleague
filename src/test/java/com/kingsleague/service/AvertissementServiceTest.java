package com.kingsleague.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kingsleague.model.Avertissement;
import com.kingsleague.model.Team;
import com.kingsleague.repository.interfaces.AvertissementRepository;

public class AvertissementServiceTest {

    @Mock
    private AvertissementRepository avertissementRepository;

    private AvertissementService avertissementService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        avertissementService = new AvertissementService();
        avertissementService.setAvertissementRepository(avertissementRepository);
    }

    @Test
    public void testAdd() {
        Avertissement avertissement = createAvertissement("ROUGE", "Team1");
        avertissementService.add(avertissement);
        verify(avertissementRepository).add(avertissement);
    }

    @Test
    public void testGetAllAvertissements() {
        List<Avertissement> expectedAvertissements = Arrays.asList(
            createAvertissement("ROUGE", "Team1"));
        
        when(avertissementRepository.findAll()).thenReturn(expectedAvertissements);
        
        List<Avertissement> result = avertissementService.getAllAvertissements();
        
        assertEquals(expectedAvertissements.size(), result.size());
        verify(avertissementRepository).findAll();
    }

    @Test
    public void testGetEquipeAvecAver() {
        Team team1 = createTeam("Team1");
        
        List<Avertissement> avertissements = Arrays.asList(
            createAvertissementWithTeam("ROUGE", team1),
            createAvertissementWithTeam(null, createTeam("Team3"))
        );
        
        when(avertissementRepository.findAll()).thenReturn(avertissements);
        
        List<Team> result = avertissementService.getEquipeAvecAver();
        
        assertEquals(1, result.size());
        assertTrue(result.contains(team1));
        verify(avertissementRepository).findAll();
    }

    @Test
    public void testGetEquipeSansAver() {
        List<Team> expectedTeams = Arrays.asList(
            createTeam("Team1"),
            createTeam("Team2")
        );
        
        when(avertissementRepository.getEquipeSansAver()).thenReturn(expectedTeams);
        
        List<Team> result = avertissementService.getEquipeSansAver();
        
        assertEquals(expectedTeams.size(), result.size());
        assertEquals(expectedTeams, result);
        verify(avertissementRepository).getEquipeSansAver();
    }

    private Team createTeam(String name) {
        Team team = new Team();
        team.setName(name);
        return team;
    }

    private Avertissement createAvertissement(String color, String teamName) {
        Team team = createTeam(teamName);
        return createAvertissementWithTeam(color, team);
    }

    private Avertissement createAvertissementWithTeam(String color, Team team) {
        Avertissement avertissement = new Avertissement();
        avertissement.setCouleur(color);
        avertissement.setTeam(team);
        avertissement.setDateAvertissement(LocalDate.now());
        return avertissement;
    }
}
