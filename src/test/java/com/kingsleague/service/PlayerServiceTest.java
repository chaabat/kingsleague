package com.kingsleague.service;

import com.kingsleague.model.Player;
import com.kingsleague.repository.interfaces.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGet() {
        Long id = 1L;
        Player player = new Player();
        player.setId(id);
        when(playerRepository.get(id)).thenReturn(Optional.of(player));

        Optional<Player> result = playerService.get(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(playerRepository).get(id);
    }

    @Test
    void testGetAll() {
        List<Player> players = Arrays.asList(new Player(), new Player());
        when(playerRepository.getAll()).thenReturn(players);

        List<Player> result = playerService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(playerRepository).getAll();
    }

    @Test
    void testAdd() {
        Player player = new Player();
        playerService.add(player);
        verify(playerRepository).add(player);
    }

    @Test
    void testUpdate() {
        Player player = new Player();
        playerService.update(player);
        verify(playerRepository).update(player);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        playerService.delete(id);
        verify(playerRepository).delete(id);
    }

    @Test
    void testGetPlayerByUsername() {
        String username = "testPlayer";
        Player player = new Player();
        player.setUsername(username);
        when(playerRepository.getByUsername(username)).thenReturn(Optional.of(player));

        Optional<Player> result = playerService.getPlayerByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(playerRepository).getByUsername(username);
    }

    @Test
    void testDeletePlayerByUsername() {
        String username = "testPlayer";
        Player player = new Player();
        player.setId(1L);
        player.setUsername(username);
        when(playerRepository.getByUsername(username)).thenReturn(Optional.of(player));

        playerService.deletePlayerByUsername(username);

        verify(playerRepository).getByUsername(username);
        verify(playerRepository).delete(1L);
    }

 
}
