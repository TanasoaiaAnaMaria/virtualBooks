package com.usv.virtualBooks.controller;

import com.usv.virtualBooks.dto.AbonamentDto;
import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.entity.Utilizator;
import com.usv.virtualBooks.service.AbonamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/abonament")
public class AbonamentController {
    private final AbonamentService abonamentService;


    public AbonamentController(AbonamentService abonamentService) {
        this.abonamentService = abonamentService;
    }

    @GetMapping
    public ResponseEntity<List<Abonament>> getUtilizatori(){
        return ResponseEntity.ok(abonamentService.getAbonamente());
    }
    @GetMapping("/id")
    public ResponseEntity<Abonament> getUtilizatorDupaId(@RequestParam UUID id) {
        return ResponseEntity.ok(abonamentService.getAbonamentDupaId(id));
    }

    @PostMapping
    public ResponseEntity<Abonament> adaugaUtilizator(@RequestBody AbonamentDto abonament) {
        return ResponseEntity.ok(abonamentService.adaugaAbonament(abonament));
    }

    @PutMapping("/addBeneficiu")
    public ResponseEntity<Abonament> adaugaBeneficiuLaAbonament(@RequestParam UUID idAbonament, @RequestParam UUID idBeneficiu){
        return ResponseEntity.ok(abonamentService.adaugaBeneficiuLaAbonament(idAbonament,idBeneficiu));
    }

    @DeleteMapping("/deleteBeneficiu")
    public ResponseEntity<Abonament> stergeBeneficiuDeLaAbonament(@RequestParam UUID idAbonament, @RequestParam UUID idBeneficiu) {
        Abonament abonament = abonamentService.stergeBeneficiuDeLaAbonament(idAbonament, idBeneficiu);
        return ResponseEntity.ok(abonament);
    }

    @GetMapping("/getBeneficii")
    public ResponseEntity<List<Beneficiu>> getBeneficiiPerAbonament(@RequestParam UUID idAbonament) {
        List<Beneficiu> beneficii = abonamentService.getBeneficiiPerAbonament(idAbonament);
        return ResponseEntity.ok(beneficii);
    }

    @PutMapping()
    public ResponseEntity<Abonament> actualizareAbonament(@RequestParam UUID id, @RequestBody AbonamentDto abonament) {
        return ResponseEntity.ok(abonamentService.actualizareAbonament(id, abonament));
    }

    @DeleteMapping("")
    public ResponseEntity<Void> stergeAbonament(@RequestParam UUID id) {
        abonamentService.stergeAbonament(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
