package com.usv.virtualBooks.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usv.virtualBooks.entity.Bonus;
import com.usv.virtualBooks.entity.Categorie;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UtilizatorDto {

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

//
//    private List<Bonus> bonusuri = new ArrayList<>();
//
//    private List<Categorie> categorii = new ArrayList<>();
}
