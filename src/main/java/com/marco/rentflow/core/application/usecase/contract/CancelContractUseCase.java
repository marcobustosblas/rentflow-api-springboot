package com.marco.rentflow.core.application.usecase.contract;

import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.core.domain.contract.RentalContract;
import com.marco.rentflow.core.domain.contract.exceptions.ContractNotFoundException;

import java.util.UUID;

public class CancelContractUseCase {

    private final ContractRepository contractRepository;

    public CancelContractUseCase(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public RentalContract execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Contract ID cannot be null");
        }

        RentalContract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ContractNotFoundException(id));

        contract.cancel();
        contractRepository.save(contract);

        return contract;
    }

}
