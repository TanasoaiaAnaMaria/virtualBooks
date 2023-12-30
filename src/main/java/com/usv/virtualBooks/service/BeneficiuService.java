package com.usv.virtualBooks.service;

import com.usv.virtualBooks.dto.BeneficiuDto;
import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.exceptions.CrudOperationException;
import com.usv.virtualBooks.repository.BeneficiuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BeneficiuService {
    public static final String MESAJ_DE_EROARE = "Nu exista beneficiu";

    public final BeneficiuRepository beneficiuRepository;

    public BeneficiuService(BeneficiuRepository beneficiuRepository) {
        this.beneficiuRepository = beneficiuRepository;
    }

    public List<Beneficiu> getBeneficiu(){
        Iterable<Beneficiu> beneficiuIterable = beneficiuRepository.findAll();
        List<Beneficiu> beneficii= new ArrayList<>();

        beneficiuIterable.forEach(beneficiu ->
                beneficii.add(Beneficiu.builder()
                        .idBeneficiu(beneficiu.getIdBeneficiu())
                        .nrCategoriiAdaugate(beneficiu.getNrCategoriiAdaugate())
                        .nrCartiAdaugate(beneficiu.getNrCartiAdaugate())
                        .abonamente(beneficiu.getAbonamente())
                        .build()));
        return beneficii;
    }

    public Beneficiu getBeneficiuDupaId(UUID id){
        return beneficiuRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });

    }

    public Beneficiu adaugaBeneficiu(BeneficiuDto beneficiu){
        Beneficiu beneficiu1=Beneficiu.builder()
                .nrCategoriiAdaugate(beneficiu.getNrCategoriiAdaugate())
                .nrCartiAdaugate(beneficiu.getNrCartiAdaugate())
                .build();

        beneficiuRepository.save(beneficiu1);
        return beneficiu1;
    }

    public Beneficiu actualizareBeneficiu (UUID id, BeneficiuDto beneficiu) {
        Beneficiu beneficiuExistent = beneficiuRepository.findById(id).orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));

        beneficiuExistent.setNrCategoriiAdaugate(beneficiu.getNrCategoriiAdaugate());
        beneficiuExistent.setNrCartiAdaugate(beneficiu.getNrCartiAdaugate());

        return beneficiuRepository.save(beneficiuExistent);
    }

    public void stergeBeneficiu (UUID id) {
        Beneficiu beneficiuExistent = beneficiuRepository.findById(id).orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));

        beneficiuRepository.delete(beneficiuExistent);
    }
}
