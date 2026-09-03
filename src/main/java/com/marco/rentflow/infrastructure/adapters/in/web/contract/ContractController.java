package com.marco.rentflow.infrastructure.adapters.in.web.contract;

import com.marco.rentflow.core.application.usecase.contract.CreateContractUseCase;
import com.marco.rentflow.core.application.usecase.contract.FindContractUseCase;
import com.marco.rentflow.core.domain.contract.RentalContract;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.dto.ContractResponseDTO;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.dto.CreateContractRequestDTO;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.mapperweb.ContractRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {

    private final CreateContractUseCase contractUseCase;
    private final FindContractUseCase findContractUseCase;

    public ContractController(CreateContractUseCase useCase, FindContractUseCase findContractUseCase) {
        this.contractUseCase = useCase;
        this.findContractUseCase = findContractUseCase;
    }

    @PostMapping
    public ResponseEntity<ContractResponseDTO> create(@Valid @RequestBody CreateContractRequestDTO request) {

        RentalContract contract = contractUseCase.execute(
                request.rut(),
                request.rentAmount(),
                request.startDate()
        );
        ContractResponseDTO responseDTO = ContractRestMapper.toResponseDTO(contract);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> getContractById(@PathVariable(name = "id") UUID id) {
        // a. El Caso de Uso de lectura busca el contrato (y lanza excepción sabrosa si no existe)
        RentalContract contract = findContractUseCase.execute(id);

        // b. Convertir el contrato (dominio) a DTO de respuesta
        ContractResponseDTO response = ContractRestMapper.toResponseDTO(contract);

        // c. Retorna HTTP 200 OK con el cuerpo JSON
        return ResponseEntity.ok(response);
    }

}
