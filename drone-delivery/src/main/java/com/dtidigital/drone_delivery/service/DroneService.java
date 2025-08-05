package com.dtidigital.drone_delivery.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.dtidigital.drone_delivery.enums.EstadoDrone;
import com.dtidigital.drone_delivery.model.Drone;
import com.dtidigital.drone_delivery.model.Pedido;
import com.dtidigital.drone_delivery.model.Entrega;
import com.dtidigital.drone_delivery.model.ZonaExclusao;

@Service
public class DroneService {

    private final List<Drone> drones = new ArrayList<>();
    private final List<Pedido> filaDePedidos = new LinkedList<>();
    private final List<Entrega> entregasRealizadas = new ArrayList<>();
    private final List<ZonaExclusao> zonasExclusao = new ArrayList<>();
    
    // Configurações do sistema
    private static final double VELOCIDADE_DRONE_KM_H = 30.0; // 30 km/h
    private static final double CONSUMO_BATERIA_POR_KM = 0.5; // 0.5 unidade de bateria por km (mais eficiente)
    private static final double BATERIA_MINIMA_RETORNO = 5.0; // 5% da bateria para retorno seguro

    public DroneService() {
        // Inicializar zonas de exclusão padrão
        inicializarZonasExclusao();
    }

    private void inicializarZonasExclusao() {
        // Zonas longe dos pontos de teste para não interferir
        zonasExclusao.add(new ZonaExclusao(-100, -100, -90, -90, "Aeroporto Central", "Zona de tráfego aéreo intenso"));
        zonasExclusao.add(new ZonaExclusao(500, 500, 700, 700, "Base Militar", "Área militar restrita"));
        zonasExclusao.add(new ZonaExclusao(-500, 1000, -300, 1200, "Hospital Principal", "Área hospitalar - sem ruído"));
    }

    public void cadastrarDrone(String id, double capacidade, double autonomia) {
        Drone novoDrone = new Drone(id, capacidade, autonomia);
        drones.add(novoDrone);
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public void adicionarPedido(Pedido pedido) {
        filaDePedidos.add(pedido);
        ordenarFila();
    }

    private void ordenarFila() {
        filaDePedidos.sort(Comparator
                .comparing(Pedido::getPrioridade)
                .thenComparing(p -> calcularDistancia(0, 0, p.getX(), p.getY())));
    }

    public void simularEntrega() {
        for (Drone drone : drones) {
            if (drone.getEstado() == EstadoDrone.IDLE && !filaDePedidos.isEmpty()) {
                alocarPedidosParaDrone(drone);
            }
        }
    }

    private void alocarPedidosParaDrone(Drone drone) {
        double pesoTotal = 0;
        List<Pedido> alocados = new ArrayList<>();
        List<Pedido> pedidosParaRemover = new ArrayList<>();

        for (Pedido pedido : filaDePedidos) {
            // Verificar se a rota passa por zona de exclusão
            if (verificarZonaExclusao(0, 0, pedido.getX(), pedido.getY())) {
                continue; // Pular este pedido
            }

            double distanciaIda = calcularDistancia(0, 0, pedido.getX(), pedido.getY());
            double distanciaVolta = distanciaIda; // Assumindo volta direta à base
            double distanciaTotal = distanciaIda + distanciaVolta;
            
            double bateriaMinimaNecessaria = distanciaTotal * CONSUMO_BATERIA_POR_KM + BATERIA_MINIMA_RETORNO;

            if ((pesoTotal + pedido.getPeso() <= drone.getCapacidadeMaxima())
                    && (bateriaMinimaNecessaria <= drone.getBateriaAtual())) {

                pesoTotal += pedido.getPeso();
                alocados.add(pedido);
                pedidosParaRemover.add(pedido);
                drone.adicionarPedido(pedido);
            }
        }

        filaDePedidos.removeAll(pedidosParaRemover);

        if (!alocados.isEmpty()) {
            executarEntregas(drone, alocados);
        }
    }

    private void executarEntregas(Drone drone, List<Pedido> pedidos) {
        drone.setEstado(EstadoDrone.CARREGANDO);
        
        // Simular tempo de carregamento
        try {
            Thread.sleep(100); // Simular 100ms de carregamento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Pedido pedido : pedidos) {
            Entrega entrega = new Entrega(drone.getId(), pedido);
            
            drone.setEstado(EstadoDrone.EM_VOO);
            
            // Calcular tempo e distância
            double distancia = calcularDistancia(drone.getPosX(), drone.getPosY(), pedido.getX(), pedido.getY());
            double tempo = calcularTempo(distancia);
            double bateriaConsumida = distancia * CONSUMO_BATERIA_POR_KM;
            
            // Consumir bateria e mover drone
            drone.consumirBateria(bateriaConsumida);
            drone.setPosicao(pedido.getX(), pedido.getY());
            
            drone.setEstado(EstadoDrone.ENTREGANDO);
            
            // Simular tempo de entrega
            try {
                Thread.sleep(50); // 50ms para entrega
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            entrega.finalizar(distancia, tempo, bateriaConsumida);
            entregasRealizadas.add(entrega);
        }

        // Retornar à base
        drone.setEstado(EstadoDrone.RETORNANDO);
        double distanciaRetorno = calcularDistancia(drone.getPosX(), drone.getPosY(), 0, 0);
        double bateriaRetorno = distanciaRetorno * CONSUMO_BATERIA_POR_KM;
        
        drone.consumirBateria(bateriaRetorno);
        drone.setPosicao(0, 0);
        drone.limparPedidos();
        
        // Verificar se precisa recarregar apenas se a bateria estiver muito baixa
        if (drone.getBateriaAtual() < BATERIA_MINIMA_RETORNO) {
            drone.setEstado(EstadoDrone.CARREGANDO);
            drone.recarregar();
        }
        
        drone.setEstado(EstadoDrone.IDLE);
    }

    private boolean verificarZonaExclusao(int x1, int y1, int x2, int y2) {
        for (ZonaExclusao zona : zonasExclusao) {
            if (zona.interceptaRota(x1, y1, x2, y2)) {
                return true;
            }
        }
        return false;
    }

    public List<Entrega> getEntregasRealizadas() {
        return entregasRealizadas;
    }

    public List<Pedido> getPedidosNaFila() {
        return new ArrayList<>(filaDePedidos);
    }

    public List<ZonaExclusao> getZonasExclusao() {
        return zonasExclusao;
    }

    public void adicionarZonaExclusao(int x1, int y1, int x2, int y2, String nome, String motivo) {
        zonasExclusao.add(new ZonaExclusao(x1, y1, x2, y2, nome, motivo));
    }

    public Map<String, Object> getEstatisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalEntregas", entregasRealizadas.size());
        stats.put("totalDrones", drones.size());
        stats.put("pedidosNaFila", filaDePedidos.size());
        
        if (!entregasRealizadas.isEmpty()) {
            double tempoMedio = entregasRealizadas.stream()
                    .mapToDouble(Entrega::getTempoTotalMinutos)
                    .average()
                    .orElse(0);
            stats.put("tempoMedioEntrega", Math.round(tempoMedio * 100.0) / 100.0);
            
            // Drone mais eficiente (que fez mais entregas)
            Map<String, Long> entregasPorDrone = entregasRealizadas.stream()
                    .collect(java.util.stream.Collectors.groupingBy(
                            Entrega::getDroneId, 
                            java.util.stream.Collectors.counting()));
            
            String droneMaisEficiente = entregasPorDrone.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("Nenhum");
            
            stats.put("droneMaisEficiente", droneMaisEficiente);
        } else {
            stats.put("tempoMedioEntrega", 0);
            stats.put("droneMaisEficiente", "Nenhum");
        }
        
        return stats;
    }

    private double calcularDistancia(int x1, int y1, int x2, int y2) {
        double dx = (double) x2 - x1;
        double dy = (double) y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double calcularTempo(double distanciaKm) {
        return (distanciaKm / VELOCIDADE_DRONE_KM_H) * 60; // Retorna tempo em minutos
    }

    public String getStatusPedido(String pedidoId) {
        // Verificar se está na fila
        for (Pedido pedido : filaDePedidos) {
            if (pedido.getId().equals(pedidoId)) {
                int posicao = filaDePedidos.indexOf(pedido) + 1;
                return "Pedido na fila, posição: " + posicao;
            }
        }
        
        // Verificar se está em entrega
        for (Drone drone : drones) {
            for (Pedido pedido : drone.getPedidosAlocados()) {
                if (pedido.getId().equals(pedidoId)) {
                    double distancia = calcularDistancia(0, 0, drone.getPosX(), drone.getPosY());
                    return "Em entrega - Drone a " + Math.round(distancia) + " metros da base";
                }
            }
        }
        
        // Verificar se foi entregue
        for (Entrega entrega : entregasRealizadas) {
            if (entrega.getPedido().getId().equals(pedidoId)) {
                return "Entregue em " + entrega.getFimEntrega().toString();
            }
        }
        
        return "Pedido não encontrado";
    }

    public boolean recarregarDrone(String droneId) {
        for (Drone drone : drones) {
            if (drone.getId().equals(droneId)) {
                drone.recarregar();
                return true;
            }
        }
        return false;
    }

    public void recarregarTodosDrones() {
        for (Drone drone : drones) {
            drone.recarregar();
        }
    }
}