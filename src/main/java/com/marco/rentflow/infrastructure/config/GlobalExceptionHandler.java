package com.marco.rentflow.infrastructure.config;

import com.marco.rentflow.core.domain.contract.exceptions.BusinessRuleException;
import com.marco.rentflow.core.domain.contract.exceptions.ContractNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. Atrapa errores de Reglas de Negocio (BusinessRuleException)
    //    → HTTP 422 Unprocessable Entity
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessRuleExceptions(BusinessRuleException ex) {
        log.warn("Violación de regla de negocio: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    // 2. Atrapa errores de Validación de Spring (@Valid)
    //    → HTTP 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().isEmpty()
                ? "Error de validación en los parámetros de entrada"
                : ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    // 3. Atrapa errores de Recurso No Encontrado (ContractNotFoundException)
    //    → HTTP 404 Not Found
    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundExceptions(ContractNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 4. Atrapa errores de Argumentos Inválidos (IllegalArgumentException)
    //    → HTTP 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 5. Atrapa errores de lectura de JSON / fechas mal formateadas
    //    → HTTP 400 Bad Request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Cuerpo de petición ilegible o formato de datos inválido: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "El cuerpo de la petición contiene JSON mal formado o un formato de fecha/tipo inválido.");
    }

    // 6. Atrapa tipos de argumentos no coincidentes (p.ej. UUIDs mal formados en la URL)
    //    → HTTP 400 Bad Request
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, String.format("El parámetro '%s' con valor '%s' no es un formato válido.", ex.getName(), ex.getValue()));
    }

    // 7. Atrapa cualquier otra excepción no controlada
    //    → HTTP 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericExceptions(Exception ex) {
        log.error("Excepción no controlada detectada en el servidor: ", ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno en el servidor"
        );
    }

    // Método privado para estructurar siempre el mismo formato JSON
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return ResponseEntity.status(status).body(error);
    }

}

