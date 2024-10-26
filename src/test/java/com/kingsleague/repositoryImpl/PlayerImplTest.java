package com.kingsleague.repositoryImpl;

import com.kingsleague.model.Player;
import com.kingsleague.repository.implementation.PlayerRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerImplTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private PlayerRepositoryImpl playerRepository;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("test-pu");
        em = emf.createEntityManager();
        playerRepository = PlayerRepositoryImpl.getInstance();
        playerRepository.setEntityManager(em);
    }

    @AfterEach
    void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void testAddAndGet() {
        Player player = new Player();
        player.setUsername("testUser");

        em.getTransaction().begin();
        playerRepository.add(player);
        em.getTransaction().commit();

        Optional<Player> retrievedPlayer = playerRepository.get(player.getId());
        assertTrue(retrievedPlayer.isPresent());
        assertEquals("testUser", retrievedPlayer.get().getUsername());
    }

    @Test
    void testUpdate() {
        Player player = new Player();
        player.setUsername("originalUser");

        em.getTransaction().begin();
        playerRepository.add(player);
        em.getTransaction().commit();

        player.setUsername("updatedUser");

        em.getTransaction().begin();
        playerRepository.update(player);
        em.getTransaction().commit();

        Optional<Player> retrievedPlayer = playerRepository.get(player.getId());
        assertTrue(retrievedPlayer.isPresent());
        assertEquals("updatedUser", retrievedPlayer.get().getUsername());
    }

    @Test
    void testDelete() {
        Player player = new Player();
        player.setUsername("userToDelete");

        em.getTransaction().begin();
        playerRepository.add(player);
        em.getTransaction().commit();

        Long playerId = player.getId();

        em.getTransaction().begin();
        playerRepository.delete(playerId);
        em.getTransaction().commit();

        Optional<Player> retrievedPlayer = playerRepository.get(playerId);
        assertFalse(retrievedPlayer.isPresent());
    }

    @Test
    void testGetAll() {
        Player player1 = new Player();
        player1.setUsername("user1");

        Player player2 = new Player();
        player2.setUsername("user2");

        em.getTransaction().begin();
        playerRepository.add(player1);
        playerRepository.add(player2);
        em.getTransaction().commit();

        List<Player> allPlayers = playerRepository.getAll();
        assertEquals(2, allPlayers.size());
        assertTrue(allPlayers.stream().anyMatch(p -> p.getUsername().equals("user1")));
        assertTrue(allPlayers.stream().anyMatch(p -> p.getUsername().equals("user2")));
    }

    @Test
    void testGetByUsername() {
        Player player = new Player();
        player.setUsername("uniqueUser");

        em.getTransaction().begin();
        playerRepository.add(player);
        em.getTransaction().commit();

        Optional<Player> retrievedPlayer = playerRepository.getByUsername("uniqueUser");
        assertTrue(retrievedPlayer.isPresent());
        assertEquals("uniqueUser", retrievedPlayer.get().getUsername());
    }
}
