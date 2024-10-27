package com.kingsleague.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.kingsleague.model.enums.TournamentStatut;

@Entity
@Table(name = "Tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GAME_ID")
    @NotNull(message = "Game cannot be null")
    private Game game;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Start date cannot be null")
    @Future(message = "Start date must be in the future")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "End date cannot be null")
    @Future(message = "End date must be in the future")
    @PastOrPresent(message = "End date must not be in the past")
    private Date endDate;

    @Min(value = 0, message = "Spectator count cannot be negative")
    private int spectatorCount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tournament_team",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    @NotEmpty(message = "Tournament must have at least one team")
    private Set<Team> teams = new HashSet<>();

    @Positive(message = "Estimated duration must be positive")
    @Min(value = 0, message = "Estimated duration cannot be negative")
    private int estimatedDuration;

    @Positive(message = "Match break time must be positive")
    @Min(value = 0, message = "Match break time cannot be negative")
    private int matchBreakTime;

    @Positive(message = "Ceremony time must be positive")
    @Min(value = 0, message = "Ceremony time cannot be negative")
    private int ceremonyTime;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tournament status cannot be null")
    private TournamentStatut status;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game2) {
        this.game = game2;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getSpectatorCount() {
        return spectatorCount;
    }

    public void setSpectatorCount(int spectatorCount) {
        this.spectatorCount = spectatorCount;
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

    public int getMatchBreakTime() {
        return matchBreakTime;
    }

    public void setMatchBreakTime(int matchBreakTime) {
        this.matchBreakTime = matchBreakTime;
    }

    public int getCeremonyTime() {
        return ceremonyTime;
    }

    public void setCeremonyTime(int ceremonyTime) {
        this.ceremonyTime = ceremonyTime;
    }

    public TournamentStatut getStatus() {
        return status;
    }

    public void setStatus(TournamentStatut status) {
        this.status = status;
    }

    public void updateStatus() {
        Date currentDate = new Date();
        if (status == TournamentStatut.CANCELED) {
            return;
        }
        if (currentDate.before(startDate)) {
            status = TournamentStatut.SCHEDULED;
        } else if (currentDate.after(startDate) && currentDate.before(endDate)) {
            status = TournamentStatut.IN_PROGRESS;
        } else if (currentDate.after(endDate)) {
            status = TournamentStatut.COMPLETED;
        }
    }

    public void cancel() {
        this.status = TournamentStatut.CANCELED;
    }
}
