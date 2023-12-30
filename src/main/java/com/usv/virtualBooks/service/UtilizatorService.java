package com.usv.virtualBooks.service;

import com.usv.virtualBooks.dto.UtilizatorDto;
import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Bonus;
import com.usv.virtualBooks.entity.Utilizator;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.exceptions.CrudOperationException;
import com.usv.virtualBooks.repository.AbonamentRepository;
import com.usv.virtualBooks.repository.BonusRepository;
import com.usv.virtualBooks.repository.UtilizatorRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UtilizatorService {

    public static final String MESAJ_DE_EROARE_UTILIZATOR = "Nu exista utilizator";
    public static final String MESAJ_DE_EROARE_ABONAMENT = "Nu exista abonament";
    public static final String MESAJ_DE_EROARE_BONUS = "Nu exista bonus";

    private final UtilizatorRepository utilizatorRepository;
    private final AbonamentRepository abonamentRepository;
    private final BonusRepository bonusRepository;

    public UtilizatorService(UtilizatorRepository utilizatorRepository, AbonamentRepository abonamentRepository, BonusRepository bonusRepository) {
        this.utilizatorRepository = utilizatorRepository;
        this.abonamentRepository = abonamentRepository;
        this.bonusRepository = bonusRepository;
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
                        .dataAbonare(utilizator.getDataAbonare())
                        .nrMaxCategorii(utilizator.getNrMaxCategorii())
                        .nrMaxCarti(utilizator.getNrMaxCarti())
                        .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                        .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                        .build()));
        return utilizatori;
    }
    public Utilizator getUtilizatorDupaId(UUID id) {
        return utilizatorRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_UTILIZATOR);
        });
    }

    public Utilizator adaugaUtilizator(UtilizatorDto utilizator) {

        Utilizator utilizator1=Utilizator.builder()
                .nume(utilizator.getNume())
                .prenume(utilizator.getPrenume())
                .email(utilizator.getEmail())
                .parola(utilizator.getParola())
                .idAbonament(utilizator.getIdAbonament())
                .dataAbonare(utilizator.getDataAbonare())
                .nrMaxCategorii(utilizator.getNrMaxCategorii())
                .nrMaxCarti(utilizator.getNrMaxCarti())
                .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                .build();
        utilizatorRepository.save(utilizator1);
        return utilizator1;

    }

    public Utilizator adaugaAbonamentLaUtilizator(UUID idUtilizator, UUID idAbonament) {
        Utilizator utilizator = utilizatorRepository.findById(idUtilizator).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_UTILIZATOR);
        });

        Abonament abonament = abonamentRepository.findById(idAbonament).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_ABONAMENT);
        });

        int sumaCartiBonusuri = abonament.getBeneficii().stream()
                .mapToInt(Beneficiu::getNrCartiAdaugate)
                .sum();
        int sumaCategoriiBonusuri = abonament.getBeneficii().stream()
                .mapToInt(Beneficiu::getNrCategoriiAdaugate)
                .sum();

        utilizator.setNrMaxCarti(utilizator.getNrMaxCarti() + sumaCartiBonusuri);
        utilizator.setNrMaxCategorii(utilizator.getNrMaxCategorii() + sumaCategoriiBonusuri);
        utilizator.setIdAbonament(abonament.getIdAbonament());
        utilizatorRepository.save(utilizator);
        return utilizator;
    }

    @Scheduled(cron = "0 0 0 1 * ?")  // Rulează în fiecare primul minut al fiecărei luni
    public void restaureazaNumarCartiLunar() {
        Iterable<Utilizator> utilizatori = utilizatorRepository.findAll();

        if (utilizatori != null) {
            for (Utilizator utilizator : utilizatori) {
                // Restaurează numărul de cărți și categorii din beneficiile bonusurilor
                int sumaCartiBonusuri = utilizator.getBonusuri().stream()
                        .flatMap(bonus -> bonus.getBeneficiiBonus().stream())
                        .mapToInt(Beneficiu::getNrCartiAdaugate)
                        .sum();

                int sumaCategoriiBonusuri = utilizator.getBonusuri().stream()
                        .flatMap(bonus -> bonus.getBeneficiiBonus().stream())
                        .mapToInt(Beneficiu::getNrCategoriiAdaugate)
                        .sum();

                Abonament abonament = abonamentRepository.findById(utilizator.getIdAbonament()).orElseThrow(() -> {
                    throw new CrudOperationException(MESAJ_DE_EROARE_ABONAMENT);
                });

                // Restaurează numărul de cărți și categorii din beneficiile abonamentelor
                int sumaCartiAbonamente = abonament.getBeneficii().stream()
                        .mapToInt(Beneficiu::getNrCartiAdaugate)
                        .sum();

                int sumaCategoriiAbonamente = abonament.getBeneficii().stream()
                        .mapToInt(Beneficiu::getNrCategoriiAdaugate)
                        .sum();

                // Actualizează numărul maxim de cărți și categorii al utilizatorului
                int numarMaxCartiNou = sumaCartiBonusuri + sumaCartiAbonamente;
                int numarMaxCategoriiNou = sumaCategoriiBonusuri + sumaCategoriiAbonamente;

                utilizator.setNrMaxCarti(numarMaxCartiNou);
                utilizator.setNrMaxCategorii(numarMaxCategoriiNou);

                // Salvează utilizatorul actualizat în baza de date
                utilizatorRepository.save(utilizator);
            }
        }
    }

    public Utilizator stergeAbonamentDeLaUtilizator(UUID idUtilizator) {
        Utilizator utilizator = utilizatorRepository.findById(idUtilizator).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_UTILIZATOR);
        });

        utilizator.setIdAbonament(null);
        utilizatorRepository.save(utilizator);
        return utilizator;
    }

    public Utilizator adaugaBonusLaUtilizator(UUID idUtilizator, UUID idBonus) {
        Utilizator utilizator = utilizatorRepository.findById(idUtilizator).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_UTILIZATOR);
        });

        Bonus bonus = bonusRepository.findById(idBonus).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_BONUS);
        });

        int numarMaxCartiNou = utilizator.getNrMaxCarti() + bonus.getBeneficiiBonus().stream()
                .mapToInt(Beneficiu::getNrCartiAdaugate)
                .sum();
        int numarMaxCategoriiNou = utilizator.getNrMaxCategorii() + bonus.getBeneficiiBonus().stream()
                .mapToInt(Beneficiu::getNrCategoriiAdaugate)
                .sum();

        utilizator.setNrMaxCarti(numarMaxCartiNou);
        utilizator.setNrMaxCategorii(numarMaxCategoriiNou);
        utilizator.getBonusuri().add(bonus);
        utilizatorRepository.save(utilizator);
        return utilizator;
    }

    public Utilizator stergeBonusDeLaUtilizator(UUID idUtilizator, UUID idBonus) {
        Utilizator utilizator = utilizatorRepository.findById(idUtilizator).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_UTILIZATOR);
        });

        Bonus bonus = bonusRepository.findById(idBonus).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_BONUS);
        });

        utilizator.getBonusuri().remove(bonus);
        utilizatorRepository.save(utilizator);
        return utilizator;
    }

    public Utilizator actualizeazaUtilizator(UUID id, UtilizatorDto utilizator) {
        Utilizator utilizatorExistent = utilizatorRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Mesaj de eroare personalizat");
        });

        utilizatorExistent.setNume(utilizator.getNume());
        utilizatorExistent.setPrenume(utilizator.getPrenume());
        utilizatorExistent.setEmail(utilizator.getEmail());
        utilizatorExistent.setParola(utilizator.getParola());
        utilizatorExistent.setIdAbonament(utilizator.getIdAbonament());
        utilizatorExistent.setDataAbonare(utilizator.getDataAbonare());
        utilizatorExistent.setNrMaxCategorii(utilizator.getNrMaxCategorii());
        utilizatorExistent.setNrMaxCarti(utilizator.getNrMaxCarti());
        utilizatorExistent.setNrCategoriiAdaugate(utilizator.getNrCategoriiAdaugate());
        utilizatorExistent.setNrCartiAdaugate(utilizator.getNrCartiAdaugate());

        return utilizatorRepository.save(utilizatorExistent);
    }

    public void stergeUtilizator(UUID id) {
        Utilizator utilizator = utilizatorRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Mesaj de eroare personalizat");
        });

        utilizatorRepository.delete(utilizator);
    }
}