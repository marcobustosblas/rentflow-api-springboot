package com.marco.rentflow.core.application.usecase.contract;

import com.marco.rentflow.core.domain.common.Rut;
import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.core.domain.contract.RentalContract;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateContractUseCase {

    private final ContractRepository contractRepository;

    public CreateContractUseCase(ContractRepository repository) {
        this.contractRepository = repository;
    }

    public RentalContract execute(String rawRut, BigDecimal rent, LocalDate startDate, LocalDate endDate) {
        if (rawRut == null || rawRut.isBlank()) {
            throw new IllegalArgumentException("RUT cannot be null or empty");
        }
        if (rent == null) {
            throw new IllegalArgumentException("Rent amount cannot be null");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }

        Rut rut = new Rut(rawRut);

        RentalContract contract = RentalContract.create(rut, rent, startDate, endDate);

        // Orquestación hacia la Infraestructura (El adaptador Postgres atrapa esta llamada)
        contractRepository.save(contract);

        return contract;
    }

}

/**
 *  NOTES
 * (Sab 15, 11:17 am)
 * Aquí en Application están los casos de uso, son clases puras de java
 * Estos orquestan mi negocio
 *
 */
