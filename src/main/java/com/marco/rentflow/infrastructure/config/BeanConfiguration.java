package com.marco.rentflow.infrastructure.config;

import com.marco.rentflow.core.application.usecase.contract.CreateContractUseCase;
import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.infrastructure.adapters.out.persistence.memory.InMemoryContractRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BeanConfiguration {

    public ContractRepository contractRepository() {
        return new InMemoryContractRepository();
    }

    public CreateContractUseCase contractUseCase() {
        ContractRepository repository = contractRepository();
        return new CreateContractUseCase(repository);
    }

    @Bean
    @Primary
    public CreateContractUseCase createContractUseCase(ContractRepository repository) {
        return new CreateContractUseCase(repository);
    }

}
