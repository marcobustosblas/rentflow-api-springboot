package com.marco.rentflow.infrastructure.adapters.out.persistence.memory;

import com.marco.rentflow.core.domain.contract.ContractRepository;
import com.marco.rentflow.core.domain.contract.RentalContract;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryContractRepository implements ContractRepository {
    // DB charcha para salir del paso
    private final Map<UUID, RentalContract> database = new HashMap<>();

    @Override
    public void save(RentalContract contract) {
        if (contract == null) {
            throw new IllegalArgumentException("Contract cannot be null");
        }
        if (contract.getId() == null) {
            throw new IllegalArgumentException("Contract ID cannot be null");
        }
        database.put(contract.getId(), contract);
    }

    @Override
    public Optional<RentalContract> findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        RentalContract contract = database.get(id);
        return Optional.ofNullable(contract);
    }

    public int size() {
        return database.size();
    }
}

/*
(14-08, 16:47 hr)
* Como memoria
- Optional.of(valor): Solo acepta valores que no son nulos.
Si le paso un null, el programa falla de inmediato con un NullPointerException.
- Optional.empty(): Crea un contenedor siempre vacío.
- Optional.ofNullable(valor): Es flexible; acepta tanto valores reales como nulos.
(Este es más sabroso y jugoso para mí)
 */
