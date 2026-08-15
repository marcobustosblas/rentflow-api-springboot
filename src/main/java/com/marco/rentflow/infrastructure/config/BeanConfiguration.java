package com.marco.rentflow.infrastructure.config;

import com.marco.rentflow.core.application.usecase.contract.CreateContractUseCase;
import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.infrastructure.adapters.out.persistence.memory.InMemoryContractRepository;

public class BeanConfiguration {

    public ContractRepository contractRepository() {
        return new InMemoryContractRepository();
    }

    public CreateContractUseCase contractUseCase() {
        ContractRepository repository = contractRepository();
        return new CreateContractUseCase(repository);
    }

}
