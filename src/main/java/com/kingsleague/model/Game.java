package com.kingsleague.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", difficulty='" + difficulty + '\'' +
                ", durationAverageMatch=" + durationAverageMatch +
                ", tournament=" + (tournament != null ? tournament.getName() : "No tournament") +
                '}';
    }
}
