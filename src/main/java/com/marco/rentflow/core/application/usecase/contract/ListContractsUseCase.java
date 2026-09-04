package com.marco.rentflow.core.application.usecase.contract;

import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.core.domain.contract.RentalContract;

import java.util.List;

public class ListContractsUseCase {

    private final ContractRepository contractRepository;

    public ListContractsUseCase(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<RentalContract> execute() {
        return contractRepository.findAll();
    }
}
