package com.usv.virtualBooks.dto;

import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.entity.Bonus;
import com.usv.virtualBooks.entity.Utilizator;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
public class BonusDto {

    private String numeBonus;

    private String conditiiBonus;

//    private List<Beneficiu> beneficiiBonus = new ArrayList<>();
//
//    private List<Utilizator> utilizatori= new ArrayList<>();
}
