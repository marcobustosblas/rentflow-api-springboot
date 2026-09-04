package com.marco.rentflow.core.application.usecase.contract;

import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.core.domain.contract.RentalContract;
import com.marco.rentflow.core.domain.contract.exceptions.ContractNotFoundException;

import java.time.LocalDate;
import java.util.UUID;

public class RenewContractUseCase {

    private final ContractRepository contractRepository;

    public RenewContractUseCase(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public RentalContract execute(UUID id, LocalDate newEndDate) {
        if (id == null) throw new IllegalArgumentException("Contract ID cannot be null");
        if (newEndDate == null) throw new IllegalArgumentException("New end date cannot be null");
        RentalContract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ContractNotFoundException(id));
        contract.renew(newEndDate);
        contractRepository.save(contract);

        return contract;
    }

}
