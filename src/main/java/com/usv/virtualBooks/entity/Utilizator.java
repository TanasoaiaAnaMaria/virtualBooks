package com.usv.virtualBooks.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usv.virtualBooks.enums.EnumTipAbonament;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUtilizator;

    private String nume;

    private String prenume;

    private String email;

    private String parola;

    private UUID idAbonament;
    @JsonFormat(pattern = "$data.configuration.format", shape = JsonFormat.Shape.STRING)
    private String dataAbonare;

    private Integer nrMaxCategorii;

    private Integer nrMaxCarti;

    private Integer nrCategoriiAdaugate;

    private Integer nrCartiAdaugate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="utilizator-bonus",
            joinColumns = @JoinColumn(name="idUtilizator"),
            inverseJoinColumns = @JoinColumn(name="idBonus")
    )
    private List<Bonus> bonusuri = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="utilizator-categorii",
            joinColumns = @JoinColumn(name="idUtilizator"),
            inverseJoinColumns = @JoinColumn(name="idCategorie")
    )
    private List<Categorie> categorii = new ArrayList<>();


}