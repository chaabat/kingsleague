package com.kingsleague.repositoryImpl;

import com.kingsleague.model.Player;
import com.kingsleague.repository.implementation.PlayerRepositoryImpl;
import com.kingsleague.repository.interfaces.PlayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Player> query;

    private PlayerRepository playerRepository;

    @Before
    public void setUp() {
        playerRepository = new PlayerRepositoryImpl();
    }

    @Test
    public void testGet() {
        Long id = 1L;
        Player player = new Player();
        player.setId(id);
        when(entityManager.find(Player.class, id)).thenReturn(player);

        Optional<Player> result = playerRepository.get(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(entityManager).find(Player.class, id);
    }

    @Test
    public void testGetAll() {
        List<Player> players = Arrays.asList(new Player(), new Player());
        when(entityManager.createQuery("SELECT p FROM Player p", Player.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(players);

        List<Player> result = playerRepository.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(entityManager).createQuery("SELECT p FROM Player p", Player.class);
        verify(query).getResultList();
    }

    @Test
    public void testAdd() {
        Player player = new Player();
        playerRepository.add(player);
        verify(entityManager).persist(player);
    }

    @Test
    public void testUpdate() {
        Player player = new Player();
        playerRepository.update(player);
        verify(entityManager).merge(player);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        Player player = new Player();
        when(entityManager.find(Player.class, id)).thenReturn(player);

        playerRepository.delete(id);

        verify(entityManager).find(Player.class, id);
        verify(entityManager).remove(player);
    }

    @Test
    public void testGetByUsername() {
        String username = "testUser";
        Player player = new Player();
        player.setUsername(username);

        when(entityManager.createQuery("SELECT p FROM Player p WHERE p.username = :username", Player.class)).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(player);

        Optional<Player> result = playerRepository.getByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(entityManager).createQuery("SELECT p FROM Player p WHERE p.username = :username", Player.class);
        verify(query).setParameter("username", username);
        verify(query).getSingleResult();
    }
}
