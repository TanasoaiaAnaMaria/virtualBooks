package com.usv.virtualBooks.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usv.virtualBooks.enums.EnumTipAbonament;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${utilizator.default.abonamentExpirat:false}")
    private Boolean abonamentExpirat;

    @Value("${utilizator.default.nrMaxCategorii:1}")
    private Integer nrMaxCategorii;

    @Value("${utilizator.default.nrMaxCarti:3}")
    private Integer nrMaxCarti;

    @Value("${utilizator.default.nrCategoriiAdaugate:0}")
    private Integer nrCategoriiAdaugate;

    @Value("${utilizator.default.nrCartiAdaugate:0}")
    private Integer nrCartiAdaugate;

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