package com.dtidigital.drone_delivery.service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.dtidigital.drone_delivery.enums.EstadoDrone;
import com.dtidigital.drone_delivery.model.Drone;

@Component
public class SimuladorTempoReal {
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final DroneService droneService;
    private boolean simulacaoAtiva = false;
    
    public SimuladorTempoReal(DroneService droneService) {
        this.droneService = droneService;
        iniciarSimulacaoAutomatica();
    }
    
    /**
     * Inicia simulação automática de entregas
     */
    public void iniciarSimulacaoAutomatica() {
        if (!simulacaoAtiva) {
            simulacaoAtiva = true;
            
            // Simular entregas a cada 10 segundos
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    if (!droneService.getPedidosNaFila().isEmpty()) {
                        droneService.simularEntrega();
                    }
                } catch (Exception e) {
                    System.err.println("Erro na simulação automática: " + e.getMessage());
                }
            }, 10, 10, TimeUnit.SECONDS);
            
            // Simular degradação de bateria a cada 30 segundos
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    simularDegradacaoBateria();
                } catch (Exception e) {
                    System.err.println("Erro na degradação de bateria: " + e.getMessage());
                }
            }, 30, 30, TimeUnit.SECONDS);
        }
    }
    
    /**
     * Para a simulação automática
     */
    public void pararSimulacao() {
        simulacaoAtiva = false;
        scheduler.shutdown();
    }
    
    /**
     * Simula degradação natural da bateria dos drones
     */
    private void simularDegradacaoBateria() {
        for (Drone drone : droneService.getDrones()) {
            // Drones em voo consomem mais bateria
            if (drone.getEstado() == EstadoDrone.EM_VOO || 
                drone.getEstado() == EstadoDrone.ENTREGANDO ||
                drone.getEstado() == EstadoDrone.RETORNANDO) {
                
                double consumoAdicional = 0.5; // 0.5 unidades por ciclo
                if (drone.getBateriaAtual() > consumoAdicional) {
                    drone.consumirBateria(consumoAdicional);
                    
                    // Se bateria crítica, forçar retorno
                    if (drone.getBateriaAtual() < 10) {
                        drone.setEstado(EstadoDrone.RETORNANDO);
                        drone.setPosicao(0, 0);
                        drone.limparPedidos();
                        drone.recarregar();
                        drone.setEstado(EstadoDrone.IDLE);
                    }
                }
            }
            
            // Drones ociosos também consomem um pouco (sistemas de bordo)
            else if (drone.getEstado() == EstadoDrone.IDLE) {
                double consumoOcioso = 0.1; // Consumo mínimo
                if (drone.getBateriaAtual() > consumoOcioso) {
                    drone.consumirBateria(consumoOcioso);
                }
            }
        }
    }
    
    /**
     * Simula eventos aleatórios no sistema
     */
    public void simularEventosAleatorios() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                double chance = Math.random();
                
                // 10% chance de tempestade (afeta todas as entregas)
                if (chance < 0.1) {
                    System.out.println("[EVENTO] ⛈️ Tempestade detectada! Drones retornando à base.");
                    forcarRetornoEmergencia();
                }
                
                // 5% chance de falha de drone
                else if (chance < 0.15) {
                    simularFalhaDrone();
                }
                
                // 20% chance de pedido urgente
                else if (chance < 0.35) {
                    // Aqui poderia gerar um pedido de alta prioridade automaticamente
                    System.out.println("[EVENTO] 🚨 Demanda alta detectada na região central.");
                }
                
            } catch (Exception e) {
                System.err.println("Erro em eventos aleatórios: " + e.getMessage());
            }
        }, 60, 120, TimeUnit.SECONDS); // A cada 2 minutos
    }
    
    /**
     * Força retorno de emergência de todos os drones
     */
    private void forcarRetornoEmergencia() {
        for (Drone drone : droneService.getDrones()) {
            if (drone.getEstado() != EstadoDrone.IDLE) {
                drone.setEstado(EstadoDrone.RETORNANDO);
                drone.setPosicao(0, 0);
                drone.limparPedidos();
                drone.setEstado(EstadoDrone.IDLE);
            }
        }
    }
    
    /**
     * Simula falha aleatória de um drone
     */
    private void simularFalhaDrone() {
        var drones = droneService.getDrones();
        if (!drones.isEmpty()) {
            Drone droneAleatorio = drones.get((int) (Math.random() * drones.size()));
            
            if (droneAleatorio.getEstado() != EstadoDrone.IDLE) {
                System.out.println("[EVENTO] ⚠️ Drone " + droneAleatorio.getId() + 
                    " apresentou falha técnica. Retornando para manutenção.");
                    
                droneAleatorio.setEstado(EstadoDrone.RETORNANDO);
                droneAleatorio.setPosicao(0, 0);
                droneAleatorio.limparPedidos();
                
                // Reduzir bateria para simular gasto de emergência
                droneAleatorio.consumirBateria(20);
                
                droneAleatorio.setEstado(EstadoDrone.IDLE);
            }
        }
    }
    
    /**
     * Gera relatório de status em tempo real
     */
    public String gerarRelatorioTempoReal() {
        var drones = droneService.getDrones();
        var pedidos = droneService.getPedidosNaFila();
        var entregas = droneService.getEntregasRealizadas();
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO TEMPO REAL ===\n");
        relatorio.append("Timestamp: ").append(LocalDateTime.now()).append("\n\n");
        
        relatorio.append("📊 ESTATÍSTICAS GERAIS:\n");
        relatorio.append("• Drones ativos: ").append(drones.size()).append("\n");
        relatorio.append("• Pedidos pendentes: ").append(pedidos.size()).append("\n");
        relatorio.append("• Entregas realizadas: ").append(entregas.size()).append("\n\n");
        
        relatorio.append("🚁 STATUS DOS DRONES:\n");
        for (Drone drone : drones) {
            relatorio.append("• ").append(drone.getId()).append(": ")
                .append(drone.getEstado()).append(" (")
                .append(String.format("%.1f", (drone.getBateriaAtual() / drone.getAutonomiaMaxima()) * 100))
                .append("% bateria)\n");
        }
        
        return relatorio.toString();
    }
    
    public boolean isSimulacaoAtiva() {
        return simulacaoAtiva;
    }
}
