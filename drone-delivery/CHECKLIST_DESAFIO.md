# 📋 CHECKLIST DO DESAFIO TÉCNICO - DTI DIGITAL

## ✅ FUNCIONALIDADES JÁ IMPLEMENTADAS

### Básicas (CRUD + APIs)
- ✅ **POST /pedidos** - Criar pedidos
- ✅ **GET /drones** - Listar drones
- ✅ **POST /drones** - Cadastrar drones
- ✅ **POST /drones/simular** - Simulação de entregas
- ✅ **GET /pedidos/entregas** - Listar entregas realizadas

### Modelo de Estados
- ✅ **Estados do Drone**: `IDLE → CARREGANDO → EM_VOO → ENTREGANDO → RETORNANDO → IDLE`
- ✅ **Gerenciamento de Estados**: Transições implementadas no DroneService

### Fila de Entrega
- ✅ **Priorização**: Por prioridade (ALTA, MEDIA, BAIXA) + distância
- ✅ **Otimização**: Combinação de pacotes por capacidade e alcance

### Interface
- ✅ **Frontend React**: Formulários e dashboard
- ✅ **Dashboard**: Visualização de drones e status

## 🚧 FUNCIONALIDADES PARA IMPLEMENTAR

### 1. Simulação Avançada de Bateria
- ⏳ **Consumo por tempo/distância**: Melhorar cálculo de bateria
- ⏳ **Recarga automática**: Drone volta à base quando bateria baixa

### 2. Obstáculos e Zonas de Exclusão
- ⏳ **Zonas de exclusão aérea**: Áreas que drones não podem atravessar
- ⏳ **Cálculo de rotas alternativas**: Desvio de obstáculos

### 3. Tempo Total de Entrega
- ⏳ **Cálculo de tempo**: Baseado em distância e velocidade
- ⏳ **Estimativas**: Para cliente e dashboard

### 4. Relatórios e Dashboard Avançado
- ⏳ **Métricas**: Quantidade de entregas, tempo médio, drone mais eficiente
- ⏳ **Visualização**: Mapa ASCII das entregas
- ⏳ **Status do cliente**: "Seu pacote está a X metros"

### 5. Testes Automatizados
- ⏳ **Testes unitários**: Cobertura das regras principais
- ⏳ **Testes de carga**: Comportamento com muitos pedidos

### 6. Tratamento de Erros Avançado
- ⏳ **Validações**: Rejeitar pacotes que ultrapassem capacidade
- ⏳ **Mensagens claras**: Para entradas inválidas

## 🎯 PRIORIDADES DE IMPLEMENTAÇÃO

### ALTA PRIORIDADE (Obrigatórias)
1. **Testes Unitários** - Requisito obrigatório
2. **Simulação de Bateria Avançada** - Core do sistema
3. **Tempo de Entrega** - Funcionalidade básica esperada
4. **Tratamento de Erros** - Robustez do sistema

### MÉDIA PRIORIDADE (Diferenciais)
5. **Relatórios e Dashboard** - Diferencial visual
6. **Status do Cliente** - UX avançada
7. **Zonas de Exclusão** - Funcionalidade criativa

### BAIXA PRIORIDADE (Extras)
8. **Mapa Visual** - Se sobrar tempo
9. **Optimizações avançadas** - Performance

## 📅 CRONOGRAMA (3 dias)

### DIA 1 (04/08) - ✅ CONCLUÍDO
- ✅ Setup inicial e correções
- ✅ CRUD básico funcionando
- ✅ Frontend React
- ✅ APIs RESTful

### DIA 2 (05/08) - 🎯 HOJE
- 🚧 Testes unitários
- 🚧 Simulação avançada de bateria
- 🚧 Cálculo de tempo de entrega
- 🚧 Tratamento de erros

### DIA 3 (06/08) - 📋 PLANEJADO
- 📋 Dashboard com métricas
- 📋 Status do cliente
- 📋 Zonas de exclusão (se der tempo)
- 📋 Documentação final
- 📋 Deploy (opcional)

## 🎯 PRÓXIMOS PASSOS IMEDIATOS

1. **Implementar testes unitários**
2. **Melhorar simulação de bateria**
3. **Adicionar cálculo de tempo**
4. **Implementar métricas no dashboard**
5. **Adicionar tratamento de erros robusto**
