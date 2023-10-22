package com.usv.virtualBooks.controller;

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
    public ResponseEntity<Utilizator> adaugaUtilizator(@RequestBody Utilizator utilizator) {
        return ResponseEntity.ok(utilizatorService.adaugaUtilizator(utilizator));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Utilizator> actualizeazaUtilizator(@PathVariable UUID id, @RequestBody Utilizator utilizator) {
//        return ResponseEntity.ok(utilizatorService.actualizeazaUtilizator(id, utilizator));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> stergeUtilizator(@PathVariable UUID id) {
//        utilizatorService.stergeUtilizator(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}