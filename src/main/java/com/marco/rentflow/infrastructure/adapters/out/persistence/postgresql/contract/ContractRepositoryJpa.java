package com.marco.rentflow.infrastructure.adapters.out.persistence.postgresql.contract;

import com.marco.rentflow.core.domain.contract.RentalContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContractRepositoryJpa extends JpaRepository<RentalContract, UUID> {

}



/**
 * (2-9, 8:51 am)
 * Mi comparación: construir un software es como una casa...
 * Primero parto por las paredes y después hago lo interno
 * Por eso, primero construyo por el out y después sigo con in
 */
