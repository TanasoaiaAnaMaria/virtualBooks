package com.usv.virtualBooks.controller;

import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Bonus;
import com.usv.virtualBooks.service.BonusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bonus")
public class BonusController {

    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @GetMapping
    public ResponseEntity<List<Bonus>> getBonusuri(){
        return ResponseEntity.ok(bonusService.getBonusuri());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Bonus> getBonusDupaId(@PathVariable UUID id) {
        return ResponseEntity.ok(bonusService.getBonusDupaId(id));
    }

    @PostMapping
    public ResponseEntity<Bonus> adaugaBonus(@RequestBody Bonus bonus) {
        return ResponseEntity.ok(bonusService.adaugaBonus(bonus));
    }

    @PutMapping("/{idBonus}/beneficiu/{idBeneficiu}")
    public ResponseEntity<Bonus> adaugaBeneficiiLaBonus(@PathVariable UUID idBonus, @PathVariable UUID idBeneficiu){
        return ResponseEntity.ok(bonusService.adaugaBeneficiiLaBonus(idBonus,idBeneficiu));
    }
}