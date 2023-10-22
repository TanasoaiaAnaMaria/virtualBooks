package com.usv.virtualBooks.entity;

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

    @ManyToMany(mappedBy = "beneficii")
    private List<Abonament> abonamente= new ArrayList<>();

}
