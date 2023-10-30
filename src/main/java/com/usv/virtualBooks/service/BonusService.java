package com.usv.virtualBooks.service;

import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.entity.Bonus;
import com.usv.virtualBooks.exceptions.CrudOperationException;
import com.usv.virtualBooks.repository.BeneficiuRepository;
import com.usv.virtualBooks.repository.BonusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BonusService {

    public static final String MESAJ_DE_EROARE = "Nu exista bonus";
    private final BonusRepository bonusRepository;
    private final BeneficiuRepository beneficiuRepository;

    public BonusService(BonusRepository bonusRepository, BeneficiuRepository beneficiuRepository) {
        this.bonusRepository = bonusRepository;
        this.beneficiuRepository = beneficiuRepository;
    }


    public List<Bonus> getBonusuri(){
        Iterable<Bonus> iterableUtilizator =bonusRepository.findAll();
        List<Bonus> bonusuri = new ArrayList<>();

        iterableUtilizator.forEach(bonus ->
                bonusuri.add(Bonus.builder()
                        .idBonus(bonus.getIdBonus())
                        .numeBonus(bonus.getNumeBonus())
                        .conditiiBonus(bonus.getConditiiBonus())
                        .beneficiiBonus(bonus.getBeneficiiBonus())
                        .build()));
        return bonusuri;
    }
    public Bonus getBonusDupaId(UUID id) {
        return bonusRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });
    }

    public Bonus adaugaBonus(Bonus bonus) {

        Bonus bonusuri=Bonus.builder()
                .idBonus(bonus.getIdBonus())
                .numeBonus(bonus.getNumeBonus())
                .conditiiBonus(bonus.getConditiiBonus())
                .build();
        bonusRepository.save(bonusuri);
        return bonusuri;

    }

    public Bonus adaugaBeneficiiLaBonus(UUID idBonus, UUID idBeneficiu){
        Bonus bonus=bonusRepository.findById(idBonus).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

        Beneficiu divizie=beneficiuRepository.findById(idBeneficiu).orElseThrow(()->{
            throw new CrudOperationException("Nu exista beneficiu");
        });

        if(bonus.getBeneficiiBonus()==null)
            bonus.setBeneficiiBonus(new ArrayList<>());

        bonus.getBeneficiiBonus().add(divizie);
        bonusRepository.save(bonus);

        return bonus;
    }

}