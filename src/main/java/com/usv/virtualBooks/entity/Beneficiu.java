package com.usv.virtualBooks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Beneficiu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idBeneficiu;

    private Integer nrCategoriiAdaugate;
    private Integer nrCartiAdaugate;

    @JsonIgnore
    @ManyToMany(mappedBy = "beneficii")
    private List<Abonament> abonamente= new ArrayList<>();

    @ManyToMany(mappedBy = "beneficiiBonus")
    private List<Bonus> bonusuri= new ArrayList<>();

}
