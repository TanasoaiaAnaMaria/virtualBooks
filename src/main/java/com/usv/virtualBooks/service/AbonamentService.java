package com.usv.virtualBooks.service;

import com.usv.virtualBooks.dto.AbonamentDto;
import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.exceptions.CrudOperationException;
import com.usv.virtualBooks.repository.AbonamentRepository;
import com.usv.virtualBooks.repository.BeneficiuRepository;
import com.usv.virtualBooks.repository.UtilizatorRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AbonamentService {
    public static final String MESAJ_DE_EROARE = "Nu exista abonament";

    public final AbonamentRepository abonamentRepository;
    public final UtilizatorRepository utilizatorRepository;
    public final BeneficiuRepository beneficiuRepository;


    public AbonamentService(AbonamentRepository abonamentRepository, UtilizatorRepository utilizatorRepository, BeneficiuRepository beneficiuRepository) {
        this.abonamentRepository = abonamentRepository;
        this.utilizatorRepository = utilizatorRepository;
        this.beneficiuRepository = beneficiuRepository;
    }

    public List<Abonament> getAbonamente(){
        Iterable<Abonament> iterableAbonament = abonamentRepository.findAll();
        List<Abonament> abonamente= new ArrayList<>();

        iterableAbonament.forEach(abonament ->
                abonamente.add(Abonament.builder()
                        .idAbonament(abonament.getIdAbonament())
                        .numeAbonament(abonament.getNumeAbonament())
                        .sumaAbonament(abonament.getSumaAbonament())
                        .utilizatori(abonament.getUtilizatori())
                        .beneficii(abonament.getBeneficii())
                        .build()));
        return abonamente;
    }

    public Abonament getAbonamentDupaId(UUID id){
        return abonamentRepository.findById(id).orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));

    }

    public Abonament adaugaAbonament(AbonamentDto abonament){
        Abonament abonament1=Abonament.builder()
                .numeAbonament(abonament.getNumeAbonament())
                .sumaAbonament(abonament.getSumaAbonament())
                .utilizatori(abonament.getUtilizatori())
                .build();

        abonamentRepository.save(abonament1);
        return abonament1;
    }

    public Abonament adaugaBeneficiuLaAbonament(UUID idAbonament, UUID idBeneficiu){
        Abonament abonament=abonamentRepository.findById(idAbonament).orElseThrow(()-> new CrudOperationException(MESAJ_DE_EROARE));

        Beneficiu divizie=beneficiuRepository.findById(idBeneficiu).orElseThrow(()-> new CrudOperationException("Nu exista beneficiu"));

        if(abonament.getBeneficii()==null)
            abonament.setBeneficii(new ArrayList<>());

        abonament.getBeneficii().add(divizie);
        abonamentRepository.save(abonament);

        return abonament;
    }

    public Abonament stergeBeneficiuDeLaAbonament(UUID idAbonament, UUID idBeneficiu) {
        Abonament abonament = abonamentRepository.findById(idAbonament)
                .orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));

        Beneficiu beneficiu = beneficiuRepository.findById(idBeneficiu)
                .orElseThrow(() -> new CrudOperationException("Nu există beneficiu"));

        if (abonament.getBeneficii() != null) {
            abonament.getBeneficii().remove(beneficiu);
            abonamentRepository.save(abonament);
        }

        return abonament;
    }

    public List<Beneficiu> getBeneficiiPerAbonament(UUID idAbonament) {
        Abonament abonament = abonamentRepository.findById(idAbonament)
                .orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));

        if (abonament.getBeneficii() != null) {
            return abonament.getBeneficii();
        } else {
            return new ArrayList<>();
        }
    }

    public Abonament actualizareAbonament (UUID id, AbonamentDto abonament) {
        Abonament abonamentExistent = abonamentRepository.findById(id).orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));

        abonamentExistent.setNumeAbonament(abonament.getNumeAbonament());
        abonamentExistent.setSumaAbonament(abonament.getSumaAbonament());

        return abonamentRepository.save(abonamentExistent);
    }

    public void stergeAbonament (UUID id) {
        Abonament abonamentExistent = abonamentRepository.findById(id).orElseThrow(() -> new CrudOperationException(MESAJ_DE_EROARE));

        abonamentRepository.delete(abonamentExistent);
    }

}
