package com.marco.rentflow.infrastructure.adapters.in.web.contract.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RenewContractRequestDTO(
        @NotNull(message = "Nueva fecha de término es obligatoria")
        LocalDate endDate
) {}