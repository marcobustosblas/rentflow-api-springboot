package com.marco.rentflow.infrastructure.adapters.in.web.contract;

import com.marco.rentflow.core.application.usecase.contract.*;
import com.marco.rentflow.core.domain.contract.RentalContract;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.dto.ContractResponseDTO;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.dto.CreateContractRequestDTO;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.dto.RenewContractRequestDTO;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.mapperweb.ContractRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {

    private final CreateContractUseCase createContractUseCase;
    private final FindContractUseCase findContractUseCase;
    private final ListContractsUseCase listContractsUseCase;
    private final CancelContractUseCase cancelContractUseCase;
    private final RenewContractUseCase renewContractUseCase;

    public ContractController(CreateContractUseCase createContractUseCase, FindContractUseCase findContractUseCase,
                              ListContractsUseCase listContractsUseCase, CancelContractUseCase cancelContractUseCase,
                              RenewContractUseCase renewContractUseCase) {
        this.createContractUseCase = createContractUseCase;
        this.findContractUseCase = findContractUseCase;
        this.listContractsUseCase = listContractsUseCase;
        this.cancelContractUseCase = cancelContractUseCase;
        this.renewContractUseCase = renewContractUseCase;
    }

    // POST /api/v1/contracts - Crear contrato
    @PostMapping
    public ResponseEntity<ContractResponseDTO> create(@Valid @RequestBody CreateContractRequestDTO request) {

        RentalContract contract = createContractUseCase.execute(
                request.rut(),
                request.rentAmount(),
                request.startDate(),
                request.endDate()
        );
        ContractResponseDTO responseDTO = ContractRestMapper.toResponseDTO(contract);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // GET /api/v1/contracts - Listar TODOS
    @GetMapping
    public ResponseEntity<List<ContractResponseDTO>> getAll() {
        List<ContractResponseDTO> responses = listContractsUseCase.execute().stream()
                .map(ContractRestMapper::toResponseDTO).toList();
        return ResponseEntity.ok(responses);
    }

    // GET /api/v1/contracts/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> getById(@PathVariable(name = "id") UUID id) {
        // a. El Caso de Uso de lectura busca el contrato (y lanza excepción sabrosa si no existe)
        RentalContract contract = findContractUseCase.execute(id);

        // b. Convertir el contrato (dominio) a DTO de respuesta
        ContractResponseDTO response = ContractRestMapper.toResponseDTO(contract);

        // c. Retorna HTTP 200 OK con el cuerpo JSON
        return ResponseEntity.ok(response);
    }

    // PATCH /api/v1/contracts/{id}/cancel - Cancelar
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ContractResponseDTO> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(ContractRestMapper.toResponseDTO(cancelContractUseCase.execute(id)));
    }

    // PATCH /api/v1/contracts/{id}/renew - Renovar
    @PatchMapping("/{id}/renew")
    public ResponseEntity<ContractResponseDTO> renew(@PathVariable(name = "id") UUID id,
                                                     @Valid @RequestBody RenewContractRequestDTO request) {
        RentalContract contract = renewContractUseCase.execute(id, request.endDate());
        ContractResponseDTO responseDTO = ContractRestMapper.toResponseDTO(contract);
        return ResponseEntity.ok(responseDTO);
    }

}
