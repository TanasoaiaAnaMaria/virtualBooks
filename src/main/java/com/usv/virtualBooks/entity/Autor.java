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
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAutor;

    private String numeAutor;

    private String prenumeAutor;

    @ManyToMany(mappedBy = "autori")
    private List<Categorie> categorii= new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="autori-carti",
            joinColumns = @JoinColumn(name="idAutor"),
            inverseJoinColumns = @JoinColumn(name="idCarte")
    )
    private List<Carte> carti = new ArrayList<>();
}