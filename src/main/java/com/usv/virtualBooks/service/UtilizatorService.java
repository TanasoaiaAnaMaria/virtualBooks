package com.usv.virtualBooks.service;

import com.usv.virtualBooks.dto.UtilizatorDto;
import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Bonus;
import com.usv.virtualBooks.entity.Utilizator;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.enums.EnumTipAbonament;
import com.usv.virtualBooks.exceptions.CrudOperationException;
import com.usv.virtualBooks.repository.AbonamentRepository;
import com.usv.virtualBooks.repository.BonusRepository;
import com.usv.virtualBooks.repository.UtilizatorRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@EnableScheduling
public class UtilizatorService {

    public static final String MESAJ_DE_EROARE_UTILIZATOR = "Nu exista utilizator";
    public static final String MESAJ_DE_EROARE_ABONAMENT = "Nu exista abonament";
    public static final String MESAJ_DE_EROARE_ABONAMENT_EXISTENT = "Acest abonament se afla deja in planul tau";
    public static final String MESAJ_DE_EROARE_ABONAMENT_GRATIS = "Nu s-a găsit abonamentul 'GRATIS'";
    public static final String MESAJ_DE_EROARE_ABONAMENT_DEJA_GRATIS = "Abonamentul utilizatorului este deja default";

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
                        .abonamentExpirat(utilizator.getAbonamentExpirat())
                        .nrMaxCategorii(utilizator.getNrMaxCategorii())
                        .nrMaxCarti(utilizator.getNrMaxCarti())
                        .nrCategoriiAdaugate(utilizator.getNrCategoriiAdaugate())
                        .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                        .bonusuri(utilizator.getBonusuri())
                        .build()));
        return utilizatori;
    }
    public Utilizator getUtilizatorDupaId(UUID id) {
        return utilizatorRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_UTILIZATOR);
        });
    }

    public Utilizator adaugaUtilizator(UtilizatorDto utilizator) throws ParseException {

        Utilizator utilizator1=Utilizator.builder()
                .nume(utilizator.getNume())
                .prenume(utilizator.getPrenume())
                .email(utilizator.getEmail())
                .parola(utilizator.getParola())
                .idAbonament(utilizator.getIdAbonament())
                .dataAbonare(utilizator.getDataAbonare())
                .abonamentExpirat(utilizator.getAbonamentExpirat())
                .nrMaxCategorii(utilizator.getNrMaxCategorii())
                .nrMaxCarti(utilizator.getNrMaxCarti())
                .nrCategoriiAdaugate(utilizator.getNrCategoriiAdaugate())
                .nrCartiAdaugate(utilizator.getNrCartiAdaugate())
                .build();
        utilizatorRepository.save(utilizator1);

        // Obțineți id-ul abonamentului gratuit
        Optional<Abonament> abonamentGratisOptional = abonamentRepository.findByNumeAbonament(EnumTipAbonament.GRATIS);

        System.out.println("Abonament GRATIS la adaugare------------------"+ abonamentGratisOptional.get().getIdAbonament());

        if (abonamentGratisOptional.isPresent()) {
            // Apelul pentru adăugarea abonamentului gratuit
            adaugaAbonamentLaUtilizator(utilizator1.getIdUtilizator(), abonamentGratisOptional.get().getIdAbonament());
        } else {
            // Tratează cazul în care nu a fost găsit abonamentul "GRATIS"
            throw new CrudOperationException(MESAJ_DE_EROARE_ABONAMENT_GRATIS);
        }

        return utilizator1;

    }

    public Utilizator adaugaAbonamentLaUtilizator(UUID idUtilizator, UUID idAbonament) throws ParseException {
        Utilizator utilizator = utilizatorRepository.findById(idUtilizator).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_UTILIZATOR);
        });

        Abonament abonament = abonamentRepository.findById(idAbonament).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_ABONAMENT);
        });

        if(utilizator.getIdAbonament()!=null && utilizator.getIdAbonament().equals(idAbonament))
        {
            throw new CrudOperationException(MESAJ_DE_EROARE_ABONAMENT_EXISTENT);
        }

        int sumaCartiBonusuri = abonament.getBeneficii().stream()
                .mapToInt(Beneficiu::getNrCartiAdaugate)
                .sum();
        int sumaCategoriiBonusuri = abonament.getBeneficii().stream()
                .mapToInt(Beneficiu::getNrCategoriiAdaugate)
                .sum();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime currentDateTime = LocalDateTime.now();

// Adaugă o lună
        LocalDateTime currentDateTimePlusOneMonth = currentDateTime.plusMonths(1);

// Converteste in formatul dorit
        String formattedDate = currentDateTimePlusOneMonth.format(formatter);

        System.out.println(formattedDate);


        utilizator.setNrMaxCarti(utilizator.getNrMaxCarti() + sumaCartiBonusuri);
        utilizator.setNrMaxCategorii(utilizator.getNrMaxCategorii() + sumaCategoriiBonusuri);
        utilizator.setIdAbonament(abonament.getIdAbonament());
        utilizator.setDataAbonare(String.valueOf(formattedDate));
        utilizator.setAbonamentExpirat(false);
        utilizatorRepository.save(utilizator);
        return utilizator;
    }

    public Utilizator reinoiesteAbonament(UUID idUtilizator, UUID idAbonament) throws ParseException {
        Utilizator utilizator = utilizatorRepository.findById(idUtilizator).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_UTILIZATOR);
        });

        Abonament abonament = abonamentRepository.findById(idAbonament).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_ABONAMENT);
        });

        // Obțineți id-ul abonamentului gratuit
        Optional<Abonament> abonamentGratisOptional = abonamentRepository.findByNumeAbonament(EnumTipAbonament.GRATIS);

        if(utilizator.getIdAbonament()!=null && !utilizator.getIdAbonament().equals(abonamentGratisOptional.get().getIdAbonament()) && utilizator.getAbonamentExpirat().equals(true))
        {
            int sumaCartiBonusuri = abonament.getBeneficii().stream()
                    .mapToInt(Beneficiu::getNrCartiAdaugate)
                    .sum();
            int sumaCategoriiBonusuri = abonament.getBeneficii().stream()
                    .mapToInt(Beneficiu::getNrCategoriiAdaugate)
                    .sum();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime currentDateTime = LocalDateTime.now();

// Adaugă o lună
            LocalDateTime currentDateTimePlusOneMonth = currentDateTime.plusMonths(1);

// Converteste in formatul dorit
            String formattedDate = currentDateTimePlusOneMonth.format(formatter);

            System.out.println(formattedDate);


            utilizator.setNrMaxCarti(utilizator.getNrMaxCarti() + sumaCartiBonusuri);
            utilizator.setNrMaxCategorii(utilizator.getNrMaxCategorii() + sumaCategoriiBonusuri);
            utilizator.setIdAbonament(abonament.getIdAbonament());
            utilizator.setDataAbonare(formattedDate);
            utilizator.setAbonamentExpirat(false);
            utilizatorRepository.save(utilizator);
        }


        return utilizator;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void verificareAbonamentExpirat() {
        Iterable<Utilizator> utilizatori = utilizatorRepository.findAll();
        if (utilizatori != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime currentDateTime = LocalDateTime.now();
            // Converteste in formatul dorit
            String dataCurenta = currentDateTime.format(formatter);

            for (Utilizator utilizator : utilizatori) {
                String dataAbonament = utilizator.getDataAbonare();

                if (dataAbonament != null && dataAbonament.equals(dataCurenta)) {
                    utilizator.setAbonamentExpirat(true);
                    utilizatorRepository.save(utilizator);
                }
            }
        }
    }

    @Scheduled(cron = "0 * * * * ?")
    public void stergeAbonamentDupa3Zile() {
        Iterable<Utilizator> utilizatori = utilizatorRepository.findAll();

        if (utilizatori != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Obțineți id-ul abonamentului gratuit
            Optional<Abonament> abonamentGratisOptional = abonamentRepository.findByNumeAbonament(EnumTipAbonament.GRATIS);

            for (Utilizator utilizator : utilizatori) {
                String dataAbonament = utilizator.getDataAbonare();

                if (dataAbonament != null) {
                    LocalDate dataAbonamentLocalDate = LocalDate.parse(dataAbonament, formatter);
                    LocalDate dataExpirare = dataAbonamentLocalDate.plusDays(3);

                    // Verifică dacă data curentă este mai mare decât data expirării abonamentului
                    if (currentDateTime.toLocalDate().isAfter(dataExpirare) && utilizator.getAbonamentExpirat().equals(true) && utilizator.getAbonamentExpirat().equals(true)) {

                        // Adaugă un nou abonament gratuit, dacă este cazul
                        System.out.println("DETECTARE ABONAMENT EXPIRAT DUPA 3 ZILE");
//                            stergeAbonamentDeLaUtilizator(utilizator.getIdUtilizator());
                        utilizator.setIdAbonament(abonamentGratisOptional.get().getIdAbonament());
                        utilizator.setAbonamentExpirat(false);

                        utilizatorRepository.save(utilizator);
                    }
                }
            }
        }
    }

//    @Scheduled(cron = "0 0 0 1 * ?")  // Rulează în fiecare primul minut al fiecărei luni
    @Scheduled(cron = "0 * * * * ?")
    public void restaureazaNumarCartiLunar() {
        Iterable<Utilizator> utilizatori = utilizatorRepository.findAll();

        if (utilizatori != null) {
            for (Utilizator utilizator : utilizatori) {
                // Restaurează numărul de cărți și categorii din beneficiile bonusurilor

                Optional<Abonament> abonamentGratisOptional = abonamentRepository.findByNumeAbonament(EnumTipAbonament.GRATIS);

                if(utilizator.getIdAbonament()!=null && !utilizator.getIdAbonament().equals(abonamentGratisOptional.get().getIdAbonament()))

                {
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
                    int numarMaxCartiNou = utilizator.getNrMaxCarti() + sumaCartiAbonamente;
                    int numarMaxCategoriiNou = utilizator.getNrMaxCategorii() + sumaCategoriiAbonamente;

                    utilizator.setNrMaxCarti(numarMaxCartiNou);
                    utilizator.setNrMaxCategorii(numarMaxCategoriiNou);

                    System.out.println("ACTUALIZARE LUNARA");

                    // Salvează utilizatorul actualizat în baza de date
                    utilizatorRepository.save(utilizator);
                }
            }
        }
    }

    public Utilizator stergeAbonamentDeLaUtilizator(UUID idUtilizator) throws ParseException {
        Utilizator utilizator = utilizatorRepository.findById(idUtilizator).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_UTILIZATOR);
        });

        Abonament abonament = abonamentRepository.findById(utilizator.getIdAbonament()).orElseThrow(() -> {
            throw new CrudOperationException(MESAJ_DE_EROARE_ABONAMENT);
        });

        // Obțineți id-ul abonamentului gratuit
        Optional<Abonament> abonamentGratisOptional = abonamentRepository.findByNumeAbonament(EnumTipAbonament.GRATIS);

        System.out.println("STERGE ABONAMENT");
        if (abonamentGratisOptional.isPresent() && !abonament.getNumeAbonament().equals(EnumTipAbonament.GRATIS)) {
            // Apelul pentru adăugarea abonamentului gratuit
            utilizator.setIdAbonament(abonamentGratisOptional.get().getIdAbonament());
            utilizator.setAbonamentExpirat(false);
        } else {
            // Tratează cazul în care nu a fost găsit abonamentul "GRATIS"
            throw new CrudOperationException(MESAJ_DE_EROARE_ABONAMENT_DEJA_GRATIS);
        }
        System.out.println(utilizator.getIdAbonament());
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