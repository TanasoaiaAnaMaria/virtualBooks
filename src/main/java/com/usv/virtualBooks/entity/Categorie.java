package com.usv.virtualBooks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCategorie;

    private String numeCategorie;

    @ManyToMany(mappedBy = "categorii")
    private List<Utilizator> utilizatori= new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="categorii-autori",
            joinColumns = @JoinColumn(name="idCategorie"),
            inverseJoinColumns = @JoinColumn(name="idAutor")
    )
    private List<Autor> autori = new ArrayList<>();

}