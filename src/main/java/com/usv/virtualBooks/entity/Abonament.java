package com.usv.virtualBooks.entity;

import com.usv.virtualBooks.enums.EnumTipAbonament;
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
public class Abonament {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAbonament;

    @Enumerated(EnumType.STRING)
    private EnumTipAbonament numeAbonament;

    private Integer sumaAbonament;


    @OneToMany(
            targetEntity = Utilizator.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="idAbonament", referencedColumnName = "idAbonament")
    private List<Utilizator> utilizatori = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="abonament-beneficiu",
            joinColumns = @JoinColumn(name="idAbonament"),
            inverseJoinColumns = @JoinColumn(name="idBeneficiu")
    )
    private List<Beneficiu> beneficii = new ArrayList<>();
}
