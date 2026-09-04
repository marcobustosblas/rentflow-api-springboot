package com.marco.rentflow.infrastructure.adapters.out.persistence.postgresql.contract.mapper;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.marco.rentflow.core.domain.common.Rut;
import com.marco.rentflow.core.domain.contract.ContractStatus;
import com.marco.rentflow.core.domain.contract.RentalContract;
import com.marco.rentflow.infrastructure.adapters.out.persistence.postgresql.contract.ContractEntity;
import org.springframework.stereotype.Component;

@Component
public class ContractPersistenceMapper {

    // Dominio Puro → Entidad JPA (Para guardar en PostgreSQL)
    public ContractEntity toEntity(RentalContract domain) {
        if (domain == null) return null;
        return new ContractEntity(
                domain.getId(),
                domain.getTenantRut().value(),
                domain.getRentAmount(),
                domain.getStartDate(),
                domain.getEndDate(),
                domain.getStatus().name()
        );
    }

    // Entidad JPA → Dominio Puro (Para retornar al Caso de Uso)
    public RentalContract toDomain(ContractEntity entity) {
        if (entity == null) return null;
        Rut rut = new Rut(entity.getTenantRut());
        return RentalContract.reconstitute(
                entity.getId(),
                rut,
                entity.getRentAmount(),
                entity.getStartDate(),
                entity.getEndDate(),
                ContractStatus.valueOf(entity.getStatus())
        );
    }
}
