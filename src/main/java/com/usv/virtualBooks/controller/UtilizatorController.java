package com.usv.virtualBooks.controller;

import com.usv.virtualBooks.dto.UtilizatorDto;
import com.usv.virtualBooks.entity.Utilizator;
import com.usv.virtualBooks.service.UtilizatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/utilizator")
public class UtilizatorController {

    private final UtilizatorService utilizatorService;

    public UtilizatorController(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

    @GetMapping
    public ResponseEntity<List<Utilizator>> getUtilizatori(){
        return ResponseEntity.ok(utilizatorService.getUtilizatori());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Utilizator> getUtilizatorDupaId(@PathVariable UUID id) {
        return ResponseEntity.ok(utilizatorService.getUtilizatorDupaId(id));
    }

    @PostMapping
    public ResponseEntity<Utilizator> adaugaUtilizator(@RequestBody UtilizatorDto utilizator) {
        return ResponseEntity.ok(utilizatorService.adaugaUtilizator(utilizator));
    }

    @PostMapping("/adauga-abonament")
    public ResponseEntity<Utilizator> adaugaAbonamentLaUtilizator(@RequestParam UUID idUtilizator, @RequestParam UUID idAbonament) {
        return ResponseEntity.ok(utilizatorService.adaugaAbonamentLaUtilizator(idUtilizator, idAbonament));
    }

    @DeleteMapping("/sterge-abonament")
    public ResponseEntity<Utilizator> stergeAbonamentDeLaUtilizator(@RequestParam UUID idUtilizator) {
        return ResponseEntity.ok(utilizatorService.stergeAbonamentDeLaUtilizator(idUtilizator));
    }

    @PostMapping("/adauga-bonus")
    public ResponseEntity<Utilizator> adaugaBonusLaUtilizator(@RequestParam UUID idUtilizator, @RequestParam UUID idBonus) {
        return ResponseEntity.ok(utilizatorService.adaugaBonusLaUtilizator(idUtilizator, idBonus));
    }

    @DeleteMapping("/sterge-bonus")
    public ResponseEntity<Utilizator> stergeBonusDeLaUtilizator(@RequestParam UUID idUtilizator, @RequestParam UUID idBonus) {
        return ResponseEntity.ok(utilizatorService.stergeBonusDeLaUtilizator(idUtilizator, idBonus));
    }


    @PutMapping()
    public ResponseEntity<Utilizator> actualizeazaUtilizator(@RequestParam UUID id, @RequestBody UtilizatorDto utilizator) {
        return ResponseEntity.ok(utilizatorService.actualizeazaUtilizator(id, utilizator));
    }

    @DeleteMapping()
    public ResponseEntity<Void> stergeUtilizator(@RequestParam UUID id) {
        utilizatorService.stergeUtilizator(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}