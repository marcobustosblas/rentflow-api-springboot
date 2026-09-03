package com.marco.rentflow.infrastructure.adapters.in.web.contract.mapperweb;

import com.marco.rentflow.core.domain.contract.RentalContract;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.dto.ContractResponseDTO;

public class ContractRestMapper {

    // Traduce el Dominio Puro a un DTO de salida
    public static ContractResponseDTO toResponseDTO(RentalContract domain) {
        if (domain == null ) return  null;
        return new ContractResponseDTO(
                domain.getId(),
                domain.getTenantRut().value(),
                domain.getRentAmount(),
                domain.getStartDate(),
                domain.getStatus().name()
        );
    }

}
