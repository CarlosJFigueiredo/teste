# Sistema de Entrega com Drones - DTI Digital 🚁

## Visão Geral

Sistema completo de simulação de entregas utilizando drones, desenvolvido com **Spring Boot** no backend e **React** no frontend. O sistema implementa funcionalidades avançadas de simulação em tempo real, otimização de rotas, simulação de bateria realística e muito mais.

## 🚀 Funcionalidades Implementadas

### ✅ Core Features
- **CRUD Completo de Drones**: Cadastro, listagem, atualização e remoção
- **CRUD Completo de Pedidos**: Gerenciamento completo de pedidos de entrega
- **Simulação Avançada de Bateria**: Consumo realístico baseado em distância, peso e condições ambientais
- **Sistema de Zonas de Exclusão**: Áreas restritas para navegação aérea
- **Cálculo Inteligente de Rotas**: Otimização usando algoritmo do vizinho mais próximo
- **Dashboard Completo**: Métricas em tempo real e visualizações

### 🎯 Funcionalidades Avançadas

#### **Simulação em Tempo Real**
- ⚡ Simulação automática que executa a cada 10 segundos
- 🔋 Degradação natural de bateria em tempo real
- 🌩️ Eventos aleatórios (tempestades, falhas de drone)
- 📊 Relatórios em tempo real com timestamps

#### **Otimização Inteligente**
- 🧠 **Algoritmo de Otimização**: Prioriza entregas por peso, prioridade e distância
- 🎯 **Combinação Inteligente**: Maximiza uso da capacidade e alcance dos drones
- 📦 **Fila de Entrega**: Sistema de prioridades (ALTA, MÉDIA, BAIXA)
- 🗺️ **Otimização de Rota**: Algoritmo do vizinho mais próximo para múltiplas entregas

#### **Modelo de Simulação Orientado a Estados**
```
IDLE → CARREGANDO → EM_VOO → ENTREGANDO → RETORNANDO → IDLE
```
- Estados realísticos com tempos de transição
- Simulação de tempo de voo baseada em velocidade real (30 km/h)
- Consumo de bateria realístico com fatores ambientais

#### **Simulador de Bateria Avançado**
- 🌪️ **Fatores Ambientais**: Vento, temperatura afetam consumo
- ⚖️ **Impacto do Peso**: Consumo adicional baseado na carga
- 🔋 **Degradação Temporal**: Bateria degrada ao longo do tempo de uso
- ⚠️ **Margem de Segurança**: Verificação de bateria suficiente antes das missões

#### **Visualização em Tempo Real**
- 🗺️ **Mapa Interativo SVG**: Visualização em tempo real das operações
- 🎯 **Posicionamento Dinâmico**: Drones, pedidos e zonas de exclusão em tempo real
- 📈 **Dashboard Avançado**: Métricas, gráficos e indicadores de performance
- 📋 **Console de Logs**: Histórico de eventos em tempo real

## 🏗️ Arquitetura do Sistema

```
drone-delivery/
├── Backend (Spring Boot)
│   ├── Controllers (REST API)
│   ├── Services (Lógica de Negócio)
│   │   ├── DroneService (Gerenciamento principal)
│   │   ├── SimuladorBateria (Simulação avançada)
│   │   ├── OtimizadorEntregas (Algoritmos de otimização)
│   │   └── SimuladorTempoReal (Eventos em tempo real)
│   ├── Models (Entidades)
│   ├── DTOs (Transfer Objects)
│   └── Tests (Unitários + Integração)
└── Frontend (React)
    ├── Components (Componentes Reutilizáveis)
    │   ├── DeliveryList (Lista de drones com tempo real)
    │   ├── DashboardAvancado (Métricas e controles)
    │   ├── MapaEntregas (Visualização em tempo real)
    │   └── Outros componentes...
    ├── Pages (Páginas Principais)
    └── Services (Integração com API)
```

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21**: Linguagem principal com recursos modernos
- **Spring Boot 3.5.4**: Framework principal para REST API
- **Jakarta Validation**: Validação robusta de dados
- **Maven**: Gerenciamento de dependências e build
- **JUnit 5**: Framework de testes unitários
- **MockMvc**: Testes de integração

### Frontend
- **React 19.1.1**: Framework frontend moderno
- **React Router DOM**: Navegação SPA
- **Axios**: Cliente HTTP para API
- **CSS3**: Estilização responsiva com gradientes e animações
- **SVG**: Renderização do mapa em tempo real

## 📊 API Endpoints

### Drones
- `POST /api/drones` - Cadastrar novo drone
- `GET /api/drones` - Listar todos os drones
- `POST /api/drones/simular` - Executar simulação de entregas
- `POST /api/drones/recarregar/{id}` - Recarregar drone específico
- `POST /api/drones/recarregar-todos` - Recarregar todos os drones
- `GET /api/drones/zonas-exclusao` - Listar zonas de exclusão
- `POST /api/drones/zonas-exclusao` - Adicionar zona de exclusão

### Pedidos
- `POST /api/pedidos` - Criar novo pedido
- `GET /api/pedidos/entregas` - Listar entregas realizadas
- `GET /api/pedidos/fila` - Listar pedidos na fila
- `GET /api/pedidos/pendentes` - Listar pedidos pendentes
- `GET /api/pedidos/status/{id}` - Obter status do pedido
- `GET /api/pedidos/estatisticas` - Obter estatísticas do sistema

### Tempo Real
- `POST /api/tempo-real/iniciar` - Iniciar simulação em tempo real
- `POST /api/tempo-real/parar` - Parar simulação em tempo real
- `GET /api/tempo-real/status` - Status da simulação
- `POST /api/tempo-real/eventos` - Simular eventos aleatórios

## 🚀 Como Executar

### Pré-requisitos
- Java 21
- Node.js 18+
- Maven 3.8+

### Backend
```bash
cd drone-delivery
mvn clean install
mvn spring-boot:run
```

O backend estará disponível em `http://localhost:8080`

### Frontend
```bash
cd drone-delivery-frontend
npm install
npm start
```

O frontend estará disponível em `http://localhost:3000`

## 🧪 Executar Testes

```bash
# Testes do Backend
cd drone-delivery
mvn test

# Testes do Frontend
cd drone-delivery-frontend
npm test
```

## 📈 Funcionalidades de Destaque

### 1. **Simulação em Tempo Real**
- Sistema automático que simula entregas continuamente
- Degradação de bateria realística
- Eventos aleatórios para simular condições reais

### 2. **Otimização Avançada**
- Algoritmos que maximizam eficiência das entregas
- Combinação inteligente de pedidos por viagem
- Roteamento otimizado usando teoria dos grafos

### 3. **Dashboard Interativo**
- Métricas em tempo real
- Controles de simulação
- Visualizações gráficas avançadas

### 4. **Mapa Visual**
- Representação gráfica do sistema
- Atualização em tempo real
- Legendas e informações contextuais

## 🎯 Algoritmos Implementados

### Otimização de Entregas
1. **Ordenação por Prioridade**: ALTA > MÉDIA > BAIXA
2. **Eficiência por Distância**: Peso/Distância para maximizar eficiência
3. **Verificação de Restrições**: Capacidade, autonomia e zonas de exclusão
4. **Algoritmo Guloso**: Para alocação rápida e eficiente

### Simulação de Bateria
1. **Consumo Base**: 0.5 unidades por km
2. **Fatores Ambientais**: Vento (+20%), Temperatura (+10%)
3. **Impacto do Peso**: +10% por kg adicional
4. **Degradação Temporal**: 0.1% por hora de uso

## 📋 Estrutura de Dados

### Drone
```java
public class Drone {
    private String id;                    // Identificador único
    private double capacidadeMaxima;      // Capacidade em kg
    private double autonomiaMaxima;       // Autonomia máxima em km
    private double bateriaAtual;          // Bateria atual
    private int posX, posY;               // Posição atual
    private EstadoDrone estado;           // Estado atual
    private List<Pedido> pedidosAlocados; // Pedidos alocados
}
```

### Pedido
```java
public class Pedido {
    private String id;                    // ID único
    private String cliente;               // Nome do cliente
    private double peso;                  // Peso do pacote
    private int x, y;                     // Coordenadas de destino
    private Prioridade prioridade;        // Prioridade da entrega
    private LocalDateTime dataCriacao;    // Timestamp de criação
}
```

## 🔧 Configurações

### CORS
O backend está configurado para aceitar requisições do frontend em `http://localhost:3000`.

### Banco de Dados
O sistema utiliza estruturas em memória para demonstração. Em produção, recomenda-se integração com banco de dados.

## 🎨 Design e UX

- **Design Responsivo**: Interface adaptável para diferentes dispositivos
- **Tema Moderno**: Gradientes e animações CSS3
- **Feedback Visual**: Indicadores de carregamento e estados
- **Acessibilidade**: Cores contrastantes e navegação por teclado

## 📝 Testes Implementados

### Testes Unitários
- Testes de modelo (Drone, Pedido)
- Testes de serviço (DroneService, SimuladorBateria)
- Cobertura de cenários de edge cases

### Testes de Integração
- Testes de API endpoints
- Simulação de entregas completas
- Validação de regras de negócio

## 🚀 Próximos Passos

- [ ] Integração com banco de dados real
- [ ] API de notificações push
- [ ] Integração com mapas reais (Google Maps)
- [ ] Sistema de autenticação
- [ ] Relatórios em PDF
- [ ] Dashboard mobile

## 👥 Contribuição

Este projeto foi desenvolvido como parte do desafio técnico da DTI Digital, demonstrando habilidades em:

- Desenvolvimento full-stack
- Arquitetura de software
- Algoritmos de otimização
- Simulação em tempo real
- Testes automatizados
- Design de interfaces

---

**Desenvolvido por**: Carlos José de Figueiredo  
**Data**: Agosto 2025  
**Tecnologias**: Java 21, Spring Boot, React, SVG, CSS3

🚁 *"Revolucionando entregas com tecnologia de ponta!"*
