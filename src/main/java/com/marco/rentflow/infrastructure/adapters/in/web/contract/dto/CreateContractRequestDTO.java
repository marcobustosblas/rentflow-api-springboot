package com.marco.rentflow.infrastructure.adapters.in.web.contract.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateContractRequestDTO(
        @NotBlank(message = "RUT es obligatorio")
        String rut,

        @NotNull(message = "Monto de arriendo es obligatorio")
        @Positive(message = "El monto debe ser mayor a cero")
        BigDecimal rentAmount,

        @NotNull(message = "Fecha de inicio es obligatoria")
        LocalDate startDate,

        @NotNull(message = "Fecha de término es obligatoria")
        LocalDate endDate
) {}