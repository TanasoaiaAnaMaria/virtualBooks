package com.usv.virtualBooks.repository;

import com.usv.virtualBooks.entity.Beneficiu;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BeneficiuRepository extends CrudRepository<Beneficiu, UUID> {
}
