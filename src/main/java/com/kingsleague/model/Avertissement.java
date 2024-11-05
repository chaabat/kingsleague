package com.kingsleague.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Avertissement")
public class Avertissement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "couleur")
    private String couleur;

    @Column(name = "dateAvertissement")
    private LocalDate dateAvertissement;

    @OneToOne
    @JoinColumn(name = "team_id", unique = true)
    private Team team;

    public LocalDate getDateAvertissement() {
        return dateAvertissement;
    }

    public void setDateAvertissement(LocalDate dateAvertissement) {
        this.dateAvertissement = dateAvertissement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

   
}
