package com.marco.rentflow.core.domain.contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository {

    void save(RentalContract contract);
    Optional<RentalContract> findById(UUID id);
    List<RentalContract> findAll();
}
