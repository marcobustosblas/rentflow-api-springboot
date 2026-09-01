package com.marco.rentflow.core.domain.contract;

import com.marco.rentflow.core.domain.common.Rut;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class RentalContract {
    private final UUID id;
    private Rut tenantRut;
    private BigDecimal rentAmount;
    private LocalDate startDate;
    private ContractStatus status; // Asumiendo que tienes un Enum, si no, usa String temporalmente

    // a Method estático de fábrica para CREAR un contrato NUEVO desde el Caso de Uso
    public static RentalContract create(Rut rut, BigDecimal rentAmount, LocalDate startDate) {
        return new RentalContract(UUID.randomUUID(), rut, rentAmount, startDate, ContractStatus.ACTIVE);
    }

    // b Method estático para RECONSTITUIR un contrato EXISTENTE desde PostgreSQL
    public static RentalContract reconstitute(UUID id, Rut rut, BigDecimal rentAmount, LocalDate startDate, ContractStatus status) {
        return new RentalContract(id, rut, rentAmount, startDate, status);
    }

    // Constructor privado para blindar la instanciación
    private RentalContract(UUID id, Rut rut, BigDecimal rentAmount, LocalDate startDate, ContractStatus status) {
        this.id = Objects.requireNonNull(id, "Id cannot be null");
        this.tenantRut = Objects.requireNonNull(rut, "Tenant RUT cannot be null");
        this.rentAmount = validateNotNegative(rentAmount);
        this.startDate = Objects.requireNonNull(startDate, "Start date cannot be null");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
    }

    // c Verbos de negocio públicos (En lugar del típico "setRentAmount")
    public void renegotiateRent(BigDecimal newRentAmount) {
        this.rentAmount = validateNotNegative(newRentAmount);
    }

    public void terminateContract() {
        this.status = ContractStatus.TERMINATED; // Ejemplo de regla de negocio
    }

    private static BigDecimal validateNotNegative(BigDecimal rent) {
        Objects.requireNonNull(rent, "Rent cannot be null");
        if (rent.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Rent amount must be greater than zero");
        }
        return rent;
    }

    // Getters
    public UUID getId() { return this.id; }
    public Rut getTenantRut() { return this.tenantRut; }
    public BigDecimal getRentAmount() { return this.rentAmount; }
    public LocalDate getStartDate() { return this.startDate; }
    public ContractStatus getStatus() { return this.status; }
}