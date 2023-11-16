package com.usv.virtualBooks.dto;

import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.entity.Utilizator;
import com.usv.virtualBooks.enums.EnumTipAbonament;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AbonamentDto {

    @Enumerated(EnumType.STRING)
    private EnumTipAbonament numeAbonament;

    private Integer sumaAbonament;

    private List<Utilizator> utilizatori = new ArrayList<>();

    private List<Beneficiu> beneficii = new ArrayList<>();
}
