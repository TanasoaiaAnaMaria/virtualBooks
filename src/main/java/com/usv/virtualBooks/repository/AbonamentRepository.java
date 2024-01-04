package com.usv.virtualBooks.repository;


import com.usv.virtualBooks.entity.Abonament;
import com.usv.virtualBooks.enums.EnumTipAbonament;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
public interface AbonamentRepository extends CrudRepository<Abonament, UUID> {
    Optional<Abonament> findByNumeAbonament(EnumTipAbonament numeAbonament);
}
