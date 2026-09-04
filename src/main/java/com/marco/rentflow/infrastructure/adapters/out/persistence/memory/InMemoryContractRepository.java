package com.marco.rentflow.infrastructure.adapters.out.persistence.memory;

import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.core.domain.contract.RentalContract;

import java.util.*;

public class InMemoryContractRepository implements ContractRepository {
    private final Map<UUID, RentalContract> database = new HashMap<>();

    @Override
    public void save(RentalContract contract) {
        if (contract == null || contract.getId() == null) {
            throw new IllegalArgumentException("Contract or its ID cannot be null");
        }
        database.put(contract.getId(), contract);
    }

    @Override
    public Optional<RentalContract> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<RentalContract> findAll() {  // NUEVO method
        return new ArrayList<>(database.values());
    }

    public int size() {
        return database.size();
    }

    public void clear() {
        database.clear();
    }
}