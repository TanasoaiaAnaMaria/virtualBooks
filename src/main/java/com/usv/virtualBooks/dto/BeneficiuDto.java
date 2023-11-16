package com.usv.virtualBooks.dto;

import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.entity.Bonus;
import com.usv.virtualBooks.entity.Utilizator;
import com.usv.virtualBooks.enums.EnumTipAbonament;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BeneficiuDto {

    private Integer nrCategoriiAdaugate;
    private Integer nrCartiAdaugate;

    @ManyToMany(mappedBy = "beneficii")
    private List<Abonament> abonamente= new ArrayList<>();

    @ManyToMany(mappedBy = "beneficiiBonus")
    private List<Bonus> bonusuri= new ArrayList<>();
}
