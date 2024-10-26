package com.kingsleague.service;

import com.kingsleague.model.Player;
import com.kingsleague.repository.interfaces.PlayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        playerService = new PlayerService();
        playerService.setPlayerRepository(playerRepository);
    }

    @Test
    public void testGetPlayerById() {
        Long playerId = 1L;
        Player expectedPlayer = new Player();
        when(playerRepository.get(playerId)).thenReturn(Optional.of(expectedPlayer));

        Optional<Player> result = playerService.getPlayerById(playerId);

        assertTrue(result.isPresent());
        assertEquals(expectedPlayer, result.get());
        verify(playerRepository).get(playerId);
    }

    @Test
    public void testGetAllPlayers() {
        List<Player> expectedPlayers = Arrays.asList(new Player(), new Player());
        when(playerRepository.getAll()).thenReturn(expectedPlayers);

        List<Player> result = playerService.getAllPlayers();

        assertEquals(expectedPlayers, result);
        verify(playerRepository).getAll();
    }

    @Test
    public void testGetPlayerByUsername() {
        String username = "testUser";
        Player expectedPlayer = new Player();
        when(playerRepository.getByUsername(username)).thenReturn(Optional.of(expectedPlayer));

        Optional<Player> result = playerService.getPlayerByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(expectedPlayer, result.get());
        verify(playerRepository).getByUsername(username);
    }

    @Test
    public void testAddPlayer() {
        Player player = new Player();

        playerService.addPlayer(player);

        verify(playerRepository).add(player);
    }

    @Test
    public void testUpdatePlayer() {
        Player player = new Player();

        playerService.updatePlayer(player);

        verify(playerRepository).update(player);
    }

    @Test
    public void testDeletePlayer() {
        Long playerId = 1L;

        playerService.deletePlayer(playerId);

        verify(playerRepository).delete(playerId);
    }

    @Test
    public void testDeletePlayerByUsername_PlayerExists() {
        String username = "testUser";
        Player player = new Player();
        player.setId(1L);
        when(playerRepository.getByUsername(username)).thenReturn(Optional.of(player));

        playerService.deletePlayerByUsername(username);

        verify(playerRepository).getByUsername(username);
        verify(playerRepository).delete(player.getId());
    }

    @Test
    public void testDeletePlayerByUsername_PlayerDoesNotExist() {
        String username = "nonExistentUser";
        when(playerRepository.getByUsername(username)).thenReturn(Optional.empty());

        playerService.deletePlayerByUsername(username);

        verify(playerRepository).getByUsername(username);
        verify(playerRepository, never()).delete(anyLong());
    }
}
