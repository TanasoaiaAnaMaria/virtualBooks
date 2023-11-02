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
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idBonus;

    private String numeBonus;

    private String conditiiBonus;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="bonus-beneficiu",
            joinColumns = @JoinColumn(name="idBonus"),
            inverseJoinColumns = @JoinColumn(name="idBeneficiu")
    )
    private List<Beneficiu> beneficiiBonus = new ArrayList<>();

    @ManyToMany(mappedBy = "bonusuri")
    private List<Utilizator> utilizatori= new ArrayList<>();

}