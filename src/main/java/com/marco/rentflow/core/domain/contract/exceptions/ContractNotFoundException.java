package com.marco.rentflow.core.domain.contract.exceptions;

import java.util.UUID;

public class ContractNotFoundException extends RuntimeException {

    public ContractNotFoundException(String message) {
        super(message);
    }

    public ContractNotFoundException(UUID id) {
        super("Contract not found with ID: " + id);
    }

    public ContractNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
