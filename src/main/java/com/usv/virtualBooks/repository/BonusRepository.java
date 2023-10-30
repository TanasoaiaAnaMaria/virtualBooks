package com.usv.virtualBooks.repository;

import com.usv.virtualBooks.entity.Bonus;
import com.usv.virtualBooks.entity.Utilizator;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BonusRepository extends CrudRepository<Bonus, UUID> {
}