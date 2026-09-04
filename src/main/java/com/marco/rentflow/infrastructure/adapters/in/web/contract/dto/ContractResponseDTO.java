package com.marco.rentflow.infrastructure.adapters.in.web.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ContractResponseDTO(
        UUID id,
        String tenantRut,
        BigDecimal rentAmount,
        LocalDate startDate,
        LocalDate endDate,
        String status
) {}