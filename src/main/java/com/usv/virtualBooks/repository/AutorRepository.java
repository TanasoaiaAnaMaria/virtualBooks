package com.usv.virtualBooks.repository;

import com.usv.virtualBooks.entity.Autor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AutorRepository extends CrudRepository<Autor, UUID> {
}