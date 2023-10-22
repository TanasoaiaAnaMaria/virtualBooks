package com.usv.virtualBooks.controller;

import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.service.BeneficiuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/beneficiu")
public class BeneficiuController {
    private final BeneficiuService beneficiuService;

    public BeneficiuController(BeneficiuService beneficiuService) {
        this.beneficiuService = beneficiuService;
    }

    @GetMapping
    public ResponseEntity<List<Beneficiu>> getBeneficii(){
        return ResponseEntity.ok(beneficiuService.getBeneficiu());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Beneficiu> getUBeneficiuDupaId(@PathVariable UUID id) {
        return ResponseEntity.ok(beneficiuService.getBeneficiuDupaId(id));
    }

    @PostMapping
    public ResponseEntity<Beneficiu> adaugaBeneficiu(@RequestBody Beneficiu beneficiu) {
        return ResponseEntity.ok(beneficiuService.adaugaBeneficiu(beneficiu));
    }

}
