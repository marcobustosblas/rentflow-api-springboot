package com.marco.rentflow.infrastructure.config;

import com.marco.rentflow.core.application.usecase.contract.*;
import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.infrastructure.adapters.out.persistence.memory.InMemoryContractRepository;
import com.marco.rentflow.infrastructure.adapters.out.persistence.postgresql.contract.ContractPostgresAdapter;
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

    // El adaptador PostgreSQL para el repositorio
    @Bean
    @Primary
    public ContractRepository contractRepository(ContractPostgresAdapter postgresAdapter) {
        return postgresAdapter;
    }

    // Caso de Uso: CREAR
    @Bean
    public CreateContractUseCase createContractUseCase(ContractRepository repository) {
        return new CreateContractUseCase(repository);
    }

    // Caso de Uso: BUSCAR por ID
    @Bean
    public FindContractUseCase findContractUseCase(ContractRepository repository) {
        return new FindContractUseCase(repository);
    }

    // Caso de Uso: LISTAR TODOS
    @Bean
    public ListContractsUseCase listContractsUseCase(ContractRepository repository) {
        return new ListContractsUseCase(repository);
    }

    // Caso de Uso: CANCELAR
    @Bean
    public CancelContractUseCase cancelContractUseCase(ContractRepository repository) {
        return new CancelContractUseCase(repository);
    }

    // Caso de Uso: RENOVAR
    @Bean
    public RenewContractUseCase renewContractUseCase(ContractRepository repository) {
        return new RenewContractUseCase(repository);
    }

}
