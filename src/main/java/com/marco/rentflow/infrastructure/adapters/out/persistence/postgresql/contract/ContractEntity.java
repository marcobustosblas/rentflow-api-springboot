package com.marco.rentflow.infrastructure.adapters.out.persistence.postgresql.contract;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contracts")
public class ContractEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Transient
    private boolean isNew = true;

    @Column(name = "tenant_rut", nullable = false, length = 12)
    private String tenantRut;

    @Column(name = "rent_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal rentAmount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PostLoad
    @PostPersist
    protected void markNotNew() {
        this.isNew = false;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public ContractEntity() {}

    public ContractEntity(UUID id, String tenantRut, BigDecimal rentAmount,
                          LocalDate startDate, LocalDate endDate, String status) {
        this.id = id;
        this.tenantRut = tenantRut;
        this.rentAmount = rentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters y Setters
    @Override
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTenantRut() { return tenantRut; }
    public void setTenantRut(String tenantRut) { this.tenantRut = tenantRut; }

    public BigDecimal getRentAmount() { return rentAmount; }
    public void setRentAmount(BigDecimal rentAmount) { this.rentAmount = rentAmount; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}