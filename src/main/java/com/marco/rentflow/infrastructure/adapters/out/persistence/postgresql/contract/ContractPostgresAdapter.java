package com.marco.rentflow.infrastructure.adapters.out.persistence.postgresql.contract;

import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.core.domain.contract.RentalContract;
import com.marco.rentflow.infrastructure.adapters.out.persistence.postgresql.contract.mapper.ContractPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ContractPostgresAdapter implements ContractRepository {

    private final SpringDataContractRepository jpaRepository;
    private final ContractPersistenceMapper mapper;

    public ContractPostgresAdapter(SpringDataContractRepository jpaRepository, ContractPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(RentalContract contract) {
        boolean exists = jpaRepository.existsById(contract.getId());
        ContractEntity entity = mapper.toEntity(contract);
        entity.setNew(!exists);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<RentalContract> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<RentalContract> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

}
