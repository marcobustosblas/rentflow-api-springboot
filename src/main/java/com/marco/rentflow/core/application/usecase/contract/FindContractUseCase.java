package com.marco.rentflow.core.application.usecase.contract;

import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.core.domain.contract.RentalContract;
import com.marco.rentflow.core.domain.contract.exceptions.ContractNotFoundException;

import java.util.UUID;

public class FindContractUseCase {

    private final ContractRepository contractRepository;

    // Inyección por constructor (Spring lo llamará automáticamente)
    public FindContractUseCase(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public RentalContract execute(UUID id) {
        // a. Validar el ID de entrada
        if (id == null) {
            throw new IllegalArgumentException("Contract ID cannot be null");
        }

        // b. Buscar en el repositorio (a través del puerto)
        return contractRepository.findById(id)
                // c. Si existe, devolver el contrato
                .orElseThrow(() -> new ContractNotFoundException(id));
    }

}
