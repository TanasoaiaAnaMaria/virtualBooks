package com.usv.virtualBooks.entity;

import com.usv.virtualBooks.enums.EnumTipAbonament;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import jakarta.persistence.*;
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

    private Integer nrMaxCategorii;

    private Integer nrMaxCarti;

    private Integer nrCategoriiAdaugate;

    private Integer nrCartiAdaugate;

}