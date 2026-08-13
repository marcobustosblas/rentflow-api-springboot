package com.marco.rentflow.core.domain.contract;

import com.marco.rentflow.core.domain.common.Rut;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class RentalContract {
    private final UUID id;
    private Rut tenantRut;
    private BigDecimal rentAmount;

    public RentalContract(Rut rut, BigDecimal rentAmount) {
        this(UUID.randomUUID(), rut, rentAmount);
    }

    public RentalContract(UUID id, Rut rut, BigDecimal rentAmount) {
        this.id = Objects.requireNonNull(id, "Id cannot be null");
        this.tenantRut = Objects.requireNonNull(rut, "Tenant RUT cannot be null");
        this.rentAmount = validateNotNegative(rentAmount);
    }

    private void updateDetails(Rut rut, BigDecimal rentAmount) {
        this.tenantRut = Objects.requireNonNull(rut, "Tenant RUT cannot be null");
        this.rentAmount = validateNotNegative(rentAmount);
    }

    private static BigDecimal validateNotNegative(BigDecimal rent) {
        Objects.requireNonNull(rent, "Rent cannot be null");
        if (rent.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Rent amount must be greater than zero");
        }
        return rent;
    }

    public UUID getId() {
        return this.id;
    }

    public Rut getTenantRut() {
        return this.tenantRut;
    }

    public BigDecimal getRentAmount() {
        return this.rentAmount;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", tenantRut=" + tenantRut +
                ", rentAmount=" + rentAmount +
                '}';
    }
}
