package com.usv.virtualBooks.controller;

import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.entity.Beneficiu;
import com.usv.virtualBooks.service.BeneficiuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/beneficiu")
@CrossOrigin(origins = "*") // Permite orice origine
public class BeneficiuController {
    private final BeneficiuService beneficiuService;

    public BeneficiuController(BeneficiuService beneficiuService) {
        this.beneficiuService = beneficiuService;
    }

//    @GetMapping
//    public ResponseEntity<List<Beneficiu>> getBeneficii(){
//        return ResponseEntity.ok(beneficiuService.getBeneficiu());
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<Beneficiu> getUBeneficiuDupaId(@PathVariable UUID id) {
//        return ResponseEntity.ok(beneficiuService.getBeneficiuDupaId(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<Beneficiu> adaugaBeneficiu(@RequestBody Beneficiu beneficiu) {
//        return ResponseEntity.ok(beneficiuService.adaugaBeneficiu(beneficiu));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Beneficiu> actualizareBeneficiu(@PathVariable UUID id, @RequestBody Beneficiu beneficiu) {
//        return ResponseEntity.ok(beneficiuService.actualizareBeneficiu(id, beneficiu));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> stergeBeneficiu(@PathVariable UUID id) {
//        beneficiuService.stergeBeneficiu(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    // În controler
    @GetMapping("/adauga")
    public String afiseazaFormularAdaugare(Model model) {
        model.addAttribute("beneficiu", new Beneficiu());
        return "formular_adaugare_beneficiu";
    }

    @PostMapping("/adauga")
    public String adaugaBeneficiuMVC(@ModelAttribute Beneficiu beneficiu, Model model) {
        Beneficiu beneficiuAdaugat = beneficiuService.adaugaBeneficiu(beneficiu);

        if (beneficiuAdaugat != null) {
            model.addAttribute("mesaj", "Beneficiu adăugat cu succes!");
        } else {
            model.addAttribute("mesaj", "Eroare la adăugarea beneficiului. Încearcă din nou.");
        }

        return "Beneficiu adaugat cu succes"; // Redirectează către lista de beneficii după adăugare
    }


    @GetMapping("/afisare")
    public ResponseEntity<List<Beneficiu>> afiseazaBeneficii() {
        List<Beneficiu> beneficii = beneficiuService.getBeneficiu();

        // Puteți adăuga orice alte verificări sau prelucrări aici

        return new ResponseEntity<>(beneficii, HttpStatus.OK);

    }
}
