package com.kingsleague.model;

import com.kingsleague.model.enums.TournamentStatut;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tournament name should not be null")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_start", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "number_spectators")
    private int numberSpectators;

    @ManyToMany(mappedBy = "tournaments")
    private List<Team> teams;

    @Column(name = "estimated_duration")
    private int estimatedDuration;

    @Column(name = "time_pause")
    private int timePause;

    @Column(name = "time_ceremony")
    private int timeCeremony;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TournamentStatut statut;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Game> games;

    // Constructors
    public Tournament() {}

    public Tournament(String name, Date dateStart, Date endDate, int numberSpectators, List<Team> teams,
                      int estimatedDuration, int timePause, int timeCeremony, TournamentStatut statut) {
        this.name = name;
        this.dateStart = dateStart;
        this.endDate = endDate;
        this.numberSpectators = numberSpectators;
        this.teams = teams;
        this.estimatedDuration = estimatedDuration;
        this.timePause = timePause;
        this.timeCeremony = timeCeremony;
        this.statut = statut;
    }

    // Getters and Setters
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

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
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
                '}';
    }
}
