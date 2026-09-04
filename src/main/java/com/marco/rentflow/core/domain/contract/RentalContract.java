package com.marco.rentflow.core.domain.contract;

import com.marco.rentflow.core.domain.common.Rut;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class RentalContract {
    private final UUID id;
    private Rut tenantRut;
    private BigDecimal rentAmount;
    private final LocalDate startDate;
    private LocalDate endDate;
    private ContractStatus status; // Asumiendo que tienes un Enum, si no, usa String temporalmente

    // a Method estático de fábrica para CREAR un contrato NUEVO desde el Caso de Uso
    public static RentalContract create(Rut rut, BigDecimal rentAmount, LocalDate startDate, LocalDate endDate) {
        return new RentalContract(UUID.randomUUID(), rut, rentAmount, startDate, endDate, ContractStatus.ACTIVE);
    }

    // b Method estático para RECONSTITUIR un contrato EXISTENTE desde PostgreSQL
    public static RentalContract reconstitute(UUID id, Rut rut, BigDecimal rentAmount, LocalDate startDate, LocalDate endDate, ContractStatus status) {
        return new RentalContract(id, rut, rentAmount, startDate, endDate, status);
    }

    // Constructor privado para blindar la instanciación
    private RentalContract(UUID id, Rut rut, BigDecimal rentAmount, LocalDate startDate, LocalDate endDate, ContractStatus status) {
        this.id = Objects.requireNonNull(id, "Id cannot be null");
        this.tenantRut = Objects.requireNonNull(rut, "Tenant RUT cannot be null");
        this.rentAmount = validateRentAmount(rentAmount);
        this.startDate = validateStartDate(startDate);
        this.endDate = validateEndDate(endDate, startDate);
        this.status = Objects.requireNonNull(status, "Status cannot be null");
    }

    private static BigDecimal validateRentAmount(BigDecimal rent) {
        Objects.requireNonNull(rent, "Rent cannot be null");
        if (rent.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Rent amount must be greater than zero");
        }
        return rent;
    }

    private static LocalDate validateStartDate(LocalDate startDate) {
        Objects.requireNonNull(startDate, "Start date cannot be null");
        return startDate;
    }

    private static LocalDate validateEndDate(LocalDate endDate, LocalDate startDate) {
        Objects.requireNonNull(endDate, "End date cannot be null");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        return endDate;
    }

    // Verbos de negocio (En lugar del conocido "setRentAmount")

    public void renegotiateRent(BigDecimal newRentAmount) {
        if (this.status != ContractStatus.ACTIVE) {
            throw new IllegalStateException("Cannot renegotiate a non-active contract");
        }
        this.rentAmount = validateRentAmount(newRentAmount);
    }

    public void renew(LocalDate newEndDate) {
        if (this.status != ContractStatus.ACTIVE) {
            throw new IllegalStateException("Cannot renew a inactive contract");
        }
        if (newEndDate.isBefore(this.endDate)) {
            throw new IllegalArgumentException("New end date must be after current end date");
        }
        this.endDate = newEndDate;
        this.status = ContractStatus.RENEWED;
    }

    public void cancel() {
        if (this.status == ContractStatus.TERMINATED) {
            throw new IllegalStateException("Contract is already terminated");
        }
        this.status = ContractStatus.TERMINATED; // Ejemplo de regla de negocio
    }

    // Methods de consultas

    public long getDurationInMonths() {
        return ChronoUnit.MONTHS.between(startDate, endDate);
    }

    public boolean isActive() {
        return this.status == ContractStatus.ACTIVE && LocalDate.now().isBefore(endDate);
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(endDate);
    }


    // Getters
    public UUID getId() {
        return this.id;
    }
    public Rut getTenantRut() {
        return this.tenantRut;
    }
    public BigDecimal getRentAmount() {
        return this.rentAmount;
    }
    public LocalDate getStartDate() {
        return this.startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public ContractStatus getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", tenantRut=" + tenantRut.value() +
                ", rentAmount=" + rentAmount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalContract that = (RentalContract) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}