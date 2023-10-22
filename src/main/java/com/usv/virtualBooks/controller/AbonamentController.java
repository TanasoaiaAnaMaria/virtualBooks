package com.usv.virtualBooks.controller;

import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Utilizator;
import com.usv.virtualBooks.service.AbonamentService;
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
    @GetMapping("/{id}")
    public ResponseEntity<Abonament> getUtilizatorDupaId(@PathVariable UUID id) {
        return ResponseEntity.ok(abonamentService.getAbonamentDupaId(id));
    }

    @PostMapping
    public ResponseEntity<Abonament> adaugaUtilizator(@RequestBody Abonament abonament) {
        return ResponseEntity.ok(abonamentService.adaugaAbonament(abonament));
    }

    @PutMapping("/{idAbonament}/beneficiu/{idBeneficiu}")
    public ResponseEntity<Abonament> adaugaBeneficiuLaAbonament(@PathVariable UUID idAbonament, @PathVariable UUID idBeneficiu){
        return ResponseEntity.ok(abonamentService.adaugaBeneficiuLaAbonament(idAbonament,idBeneficiu));
    }
}
