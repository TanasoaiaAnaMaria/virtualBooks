package com.usv.virtualBooks.service;

import com.usv.virtualBooks.entity.Utilizator;
import com.usv.virtualBooks.exceptions.CrudOperationException;
import com.usv.virtualBooks.repository.UtilizatorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UtilizatorService {

    public static final String MESAJ_DE_EROARE = "Nu exista utilizator";
    private final UtilizatorRepository utilizatorRepository;

    public UtilizatorService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    public List<Utilizator> getUtilizatori(){
        Iterable<Utilizator> iterableUtilizator =utilizatorRepository.findAll();
        List<Utilizator> utilizatori = new ArrayList<>();

        iterableUtilizator.forEach(utilizator ->
                utilizatori.add(Utilizator.builder()
                        .idUtilizator(utilizator.getIdUtilizator())
                        .nume(utilizator.getNume())
                        .prenume(utilizator.getPrenume())
                        .email(utilizator.getEmail())
                        .parola(utilizator.getParola())
                        .idAbonament(utilizator.getIdAbonament())
                        .nrMaxCategorii(utilizator.getNrMaxCategorii())
                        .nrMaxCarti(utilizator.getNrMaxCarti())
                        .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                        .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                        .build()));
        return utilizatori;
    }
    public Utilizator getUtilizatorDupaId(UUID id) {
        return utilizatorRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE);
        });
    }

    public Utilizator adaugaUtilizator(Utilizator utilizator) {

        Utilizator utilizator1=Utilizator.builder()
                .idUtilizator(utilizator.getIdUtilizator())
                .nume(utilizator.getNume())
                .prenume(utilizator.getPrenume())
                .email(utilizator.getEmail())
                .parola(utilizator.getParola())
                .idAbonament(utilizator.getIdAbonament())
                .nrMaxCategorii(utilizator.getNrMaxCategorii())
                .nrMaxCarti(utilizator.getNrMaxCarti())
                .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                .build();
        utilizatorRepository.save(utilizator1);
        return utilizator1;

    }

//    public Utilizator actualizeazaUtilizator(UUID id, Utilizator utilizator) {
//        Utilizator utilizatorExistent = utilizatorRepository.findById(id).orElseThrow(() -> {
//            throw new CrudOperationException("Mesaj de eroare personalizat");
//        });
//
//        // Actualizați atributele utilizatorului existent aici
//
//        return utilizatorRepository.save(utilizatorExistent);
//    }
//
//    public void stergeUtilizator(UUID id) {
//        Utilizator utilizator = utilizatorRepository.findById(id).orElseThrow(() -> {
//            throw new CrudOperationException("Mesaj de eroare personalizat");
//        });
//
//        utilizatorRepository.delete(utilizator);
//    }
}