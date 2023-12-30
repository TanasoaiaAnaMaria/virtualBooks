package com.usv.virtualBooks.service;

import com.usv.virtualBooks.dto.BonusDto;
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

    public Bonus adaugaBonus(BonusDto bonusDto) {
        Bonus bonus = Bonus.builder()
                .numeBonus(bonusDto.getNumeBonus())
                .conditiiBonus(bonusDto.getConditiiBonus())
                .build();

        bonusRepository.save(bonus);
        return bonus;
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

    public Bonus stergeBeneficiuDeLaBonus(UUID idBonus, UUID idBeneficiu) {
        Bonus bonus = bonusRepository.findById(idBonus).orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));
        Beneficiu beneficiu = beneficiuRepository.findById(idBeneficiu).orElseThrow(() -> new CrudOperationException("Nu exista beneficiu"));

        if (bonus.getBeneficiiBonus() != null) {
            bonus.getBeneficiiBonus().remove(beneficiu);
            bonusRepository.save(bonus);
        }

        return bonus;
    }

    public List<Beneficiu> getBeneficiiPerBonus(UUID idBonus) {
        Bonus bonus = bonusRepository.findById(idBonus).orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));

        if (bonus.getBeneficiiBonus() != null) {
            return bonus.getBeneficiiBonus();
        } else {
            return new ArrayList<>();
        }
    }

    public Bonus updateBonus(UUID id, BonusDto bonus) {
        Bonus bonusExistent = bonusRepository.findById(id).orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));

        bonusExistent.setNumeBonus(bonus.getNumeBonus());
        bonusExistent.setConditiiBonus(bonus.getConditiiBonus());

        return bonusRepository.save(bonusExistent);
    }

    public void deleteBonus(UUID id) {
        Bonus bonusExistent = bonusRepository.findById(id).orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));
        bonusRepository.delete(bonusExistent);
    }

}