package com.usv.virtualBooks.repository;

import com.usv.virtualBooks.entity.Categorie;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategorieRepository extends CrudRepository<Categorie, UUID> {
}