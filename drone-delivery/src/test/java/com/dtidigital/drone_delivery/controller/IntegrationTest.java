package com.dtidigital.drone_delivery.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve realizar fluxo completo: cadastrar drone, criar pedido e simular entrega")
    void deveRealizarFluxoCompleto() throws Exception {
        // 1. Cadastrar drone
        String droneJson = """
            {
                "id": "DRONE-TEST-001",
                "capacidade": 10.0,
                "autonomia": 100.0
            }
            """;
        
        mockMvc.perform(post("/drones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(droneJson))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("cadastrado com sucesso")));

        // 2. Verificar drone cadastrado
        mockMvc.perform(get("/drones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("DRONE-TEST-001"));

        // 3. Criar pedido
        String pedidoJson = """
            {
                "x": 5,
                "y": 5,
                "peso": 3.0,
                "prioridade": "ALTA"
            }
            """;
        
        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pedidoJson))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("adicionado com sucesso")));

        // 4. Simular entrega
        mockMvc.perform(post("/drones/simular"))
                .andExpect(status().isOk())
                .andExpect(content().string("Simulação executada."));

        // 5. Verificar entregas realizadas
        mockMvc.perform(get("/pedidos/entregas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].pedido.x").value(5))
                .andExpect(jsonPath("$[0].pedido.y").value(5));

        // 6. Verificar estatísticas
        mockMvc.perform(get("/pedidos/estatisticas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalEntregas").value(1))
                .andExpect(jsonPath("$.totalDrones").value(1))
                .andExpect(jsonPath("$.droneMaisEficiente").value("DRONE-TEST-001"));
    }

    @Test
    @DisplayName("Deve validar campos obrigatórios do drone")
    void deveValidarCamposObrigatoriosDrone() throws Exception {
        String droneInvalido = """
            {
                "id": "",
                "capacidade": 0.05,
                "autonomia": 0.5
            }
            """;
        
        mockMvc.perform(post("/drones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(droneInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve validar campos obrigatórios do pedido")
    void deveValidarCamposObrigatoriosPedido() throws Exception {
        String pedidoInvalido = """
            {
                "x": null,
                "y": 5,
                "peso": 0.05,
                "prioridade": "ALTA"
            }
            """;
        
        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pedidoInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve gerenciar zonas de exclusão")
    void deveGerenciarZonasExclusao() throws Exception {
        // Listar zonas iniciais
        mockMvc.perform(get("/drones/zonas-exclusao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Adicionar nova zona
        String zonaJson = """
            {
                "x1": 20,
                "y1": 20,
                "x2": 30,
                "y2": 30,
                "nome": "Zona Teste",
                "motivo": "Teste de integração"
            }
            """;
        
        mockMvc.perform(post("/drones/zonas-exclusao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(zonaJson))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("adicionada com sucesso")));
    }

    @Test
    @DisplayName("Deve consultar status de pedido inexistente")
    void deveConsultarStatusPedidoInexistente() throws Exception {
        mockMvc.perform(get("/pedidos/status/pedido-inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Pedido não encontrado"));
    }
}
