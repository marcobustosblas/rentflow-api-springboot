package com.marco.rentflow.infrastructure.adapters.in.web.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.dto.CreateContractRequestDTO;
import com.marco.rentflow.infrastructure.adapters.in.web.contract.dto.RenewContractRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Al cancelar un contrato y luego renovarlo, debe retornar HTTP 422 (BusinessRuleException) y no HTTP 500")
    void cancelAndThenRenew_ShouldReturn422UnprocessableEntity() throws Exception {
        // 1. Crear contrato
        CreateContractRequestDTO createDTO = new CreateContractRequestDTO(
                "12345678-5",
                new BigDecimal("500000.00"),
                LocalDate.now(),
                LocalDate.now().plusMonths(12)
        );

        MvcResult createResult = mockMvc.perform(post("/api/v1/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJson = createResult.getResponse().getContentAsString();
        String idStr = objectMapper.readTree(responseJson).get("id").asText();
        UUID contractId = UUID.fromString(idStr);

        // 2. Cancelar el contrato
        mockMvc.perform(patch("/api/v1/contracts/{id}/cancel", contractId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("TERMINATED"));

        // 3. Intentar renovar el contrato cancelado
        RenewContractRequestDTO renewDTO = new RenewContractRequestDTO(LocalDate.now().plusMonths(24));

        mockMvc.perform(patch("/api/v1/contracts/{id}/renew", contractId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(renewDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.error").value("Unprocessable Entity"))
                .andExpect(jsonPath("$.message").value("Cannot renew a contract with status: TERMINATED"));
    }
}
