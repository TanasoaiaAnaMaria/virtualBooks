package com.usv.virtualBooks.entity;

import com.usv.virtualBooks.enums.EnumTipCarte;
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
public class Carte {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCarte;

    private String numeCarte;

    private Integer nrPagini;

    private String edituraCarte;

    private EnumTipCarte tipCarte;

    @ManyToMany(mappedBy = "carti")
    private List<Autor> autori= new ArrayList<>();

}