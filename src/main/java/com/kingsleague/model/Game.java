package com.kingsleague.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Game name cannot be null")
    @Size(min = 3, max = 30, message = "Game name must be between 3 and 30 characters")
    private String name;

    @Min(value = 1, message = "Difficulty must be at least 1")
    @Max(value = 10, message = "Difficulty cannot be more than 10")
    private int difficulty;

    @Min(value = 1, message = "Average match duration must be at least 1 minute")
    @Max(value = 180, message = "Average match duration cannot be more than 180 minutes")
    private int averageMatchDuration;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<Tournament> tournaments = new ArrayList<>();

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getAverageMatchDuration() {
        return averageMatchDuration;
    }

    public void setAverageMatchDuration(int averageMatchDuration) {
        this.averageMatchDuration = averageMatchDuration;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

}
