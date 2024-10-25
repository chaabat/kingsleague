package com.kingsleague.model;

import com.kingsleague.model.enums.TournamentStatut;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tournament name should not be null")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_start")
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "number_spectators")
    private int numberSpectators;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "tournament_team",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    @Column(name = "estimated_duration")
    private int estimatedDuration;

    @Column(name = "time_pause")
    private int timePause;

    @Column(name = "time_ceremony")
    private int timeCeremony;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TournamentStatut statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberSpectators() {
        return numberSpectators;
    }

    public void setNumberSpectators(int numberSpectators) {
        this.numberSpectators = numberSpectators;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public int getTimePause() {
        return timePause;
    }

    public void setTimePause(int timePause) {
        this.timePause = timePause;
    }

    public int getTimeCeremony() {
        return timeCeremony;
    }

    public void setTimeCeremony(int timeCeremony) {
        this.timeCeremony = timeCeremony;
    }

    public TournamentStatut getStatut() {
        return statut;
    }

    public void setStatut(TournamentStatut statut) {
        this.statut = statut;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateStart=" + dateStart +
                ", endDate=" + endDate +
                ", numberSpectators=" + numberSpectators +
                ", estimatedDuration=" + estimatedDuration +
                ", timePause=" + timePause +
                ", timeCeremony=" + timeCeremony +
                ", statut=" + statut +
                ", game=" + (game != null ? game.getName() : "No Game") +
                '}';
    }
}
