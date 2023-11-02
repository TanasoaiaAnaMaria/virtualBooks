package com.usv.virtualBooks.repository;

import com.usv.virtualBooks.entity.Carte;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CarteRepository extends CrudRepository<Carte, UUID> {
}