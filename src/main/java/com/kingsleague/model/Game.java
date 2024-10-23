package com.kingsleague.model;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    @Column(name = "duration_average_match", nullable = false)
    private String durationAverageMatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    // Constructors
    public Game() {}

    public Game(String difficulty, String durationAverageMatch, Tournament tournament) {
        this.difficulty = difficulty;
        this.durationAverageMatch = durationAverageMatch;
        this.tournament = tournament;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDurationAverageMatch() {
        return durationAverageMatch;
    }

    public void setDurationAverageMatch(String durationAverageMatch) {
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
                ", durationAverageMatch='" + durationAverageMatch + '\'' +
                ", tournament=" + (tournament != null ? tournament.getName() : "No tournament") +
                '}';
    }
}
