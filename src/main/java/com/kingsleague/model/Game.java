package com.kingsleague.model;

import com.kingsleague.repository.interfaces.GameRepository;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Game name cannot be null")
    @Column(name = "name" , nullable = false)
    private String name;



    @Column(name = "difficulty", nullable = false)
    @Min(value = 1, message = "Difficulty must be at least 1")
    @Max(value = 6, message = "Difficulty cannot exceed 6")
    private int difficulty;

    // Change to int since it's a numeric value
    @Column(name = "duration_average_match", nullable = false)
    @Min(value = 1, message = "Average match duration must be at least 1 minute")
    @Max(value = 180, message = "Average match duration cannot exceed 180 minutes")
    private int durationAverageMatch;

    @OneToMany(mappedBy = "game")
    private Set<Tournament> tournaments = new HashSet<>();

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    @ManyToMany
    private Set<Team> teams = new HashSet<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public   String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDurationAverageMatch() {
        return durationAverageMatch;
    }

    public void setDurationAverageMatch(int durationAverageMatch) {
        this.durationAverageMatch = durationAverageMatch;
    }


    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", durationAverageMatch=" + durationAverageMatch +
                ", tournaments=" + tournaments +
                ", teams=" + teams +
                '}';
    }
}
