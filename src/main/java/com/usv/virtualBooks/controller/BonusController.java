package com.usv.virtualBooks.controller;

import com.usv.virtualBooks.dto.BonusDto;
import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.entity.Bonus;
import com.usv.virtualBooks.service.BonusService;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/id")
    public ResponseEntity<Bonus> getBonusDupaId(@RequestParam UUID id) {
        return ResponseEntity.ok(bonusService.getBonusDupaId(id));
    }

    @PostMapping
    public ResponseEntity<Bonus> adaugaBonus(@RequestBody BonusDto bonus) {
        return ResponseEntity.ok(bonusService.adaugaBonus(bonus));
    }

    @PutMapping("/addBeneficiu")
    public ResponseEntity<Bonus> adaugaBeneficiiLaBonus(@RequestParam UUID idBonus, @RequestParam UUID idBeneficiu){
        return ResponseEntity.ok(bonusService.adaugaBeneficiiLaBonus(idBonus,idBeneficiu));
    }

    @DeleteMapping("/deleteBeneficiu")
    public ResponseEntity<Bonus> stergeBeneficiuDeLaBonus(@RequestParam UUID idBonus, @RequestParam UUID idBeneficiu) {
        return ResponseEntity.ok(bonusService.stergeBeneficiuDeLaBonus(idBonus, idBeneficiu));
    }

    @GetMapping("/getBeneficii")
    public ResponseEntity<List<Beneficiu>> getBeneficiiPerBonus(@RequestParam UUID idBonus) {
        return ResponseEntity.ok(bonusService.getBeneficiiPerBonus(idBonus));
    }

    @PutMapping()
    public ResponseEntity<Bonus> updateBonus(@RequestParam UUID id, @RequestBody BonusDto bonusDto) {
        return ResponseEntity.ok(bonusService.updateBonus(id, bonusDto));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteBonus(@RequestParam UUID id) {
        bonusService.deleteBonus(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}