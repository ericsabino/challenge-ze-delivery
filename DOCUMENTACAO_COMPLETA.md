# Documentação Completa - Sistema de Gestão de Parceiros Zé Delivery

## Visão Geral do Projeto

Este repositório contém o **Sistema de Gestão de Parceiros do Zé Delivery**, uma API REST desenvolvida para gerenciar parceiros de entrega em um sistema de delivery. O projeto foi desenvolvido como desafio backend e implementa funcionalidades essenciais para cadastro, consulta e busca geolocalizada de parceiros.

### Propósito Principal
O sistema permite:
- **Cadastro de parceiros** com áreas de cobertura geográficas
- **Busca por localização** utilizando coordenadas geográficas
- **Consulta individual** de parceiros por identificador
- **Cache inteligente** para otimização de performance
- **Monitoramento** e observabilidade da aplicação

## Arquitetura do Sistema

### Clean Architecture
O projeto segue rigorosamente os princípios da **Clean Architecture**, garantindo separação clara de responsabilidades:

```
┌─────────────────────────────────────────────────────────┐
│                    ENTRYPOINTS                          │
│  ┌─────────────────────────────────────────────────┐    │
│  │              REST Controllers                   │    │
│  │           (ParceiroZeController)                │    │
│  └─────────────────────────────────────────────────┘    │
└─────────────────────────┬───────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────┐
│                    USE CASES                            │
│  ┌─────────────────────────────────────────────────┐    │
│  │         Business Logic & Rules                  │    │
│  │  • ParceiroZeUsecase                           │    │
│  │  • ParceiroZePorIdUsecase                      │    │
│  │  • ParceiroZePorCoordenadaUsecase              │    │
│  └─────────────────────────────────────────────────┘    │
└─────────────────────────┬───────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────┐
│                   GATEWAYS                              │
│  ┌─────────────────────────────────────────────────┐    │
│  │            Interface Contracts                  │    │
│  └─────────────────────────────────────────────────┘    │
└─────────────────────────┬───────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────┐
│                 DATA PROVIDERS                          │
│  ┌─────────────────────────────────────────────────┐    │
│  │  • MongoDB Integration                          │    │
│  │  • Redis Cache                                 │    │
│  │  • External Services                           │    │
│  └─────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────┘
```

### Estrutura de Diretórios

```
src/main/java/br/com/zedelivery/parceiroze/
├── ParceirozeApplication.java          # Classe principal Spring Boot
├── core/                               # Núcleo da aplicação (regras de negócio)
│   ├── gateway/                        # Interfaces dos contratos
│   ├── usecase/                        # Casos de uso (regras de negócio)
│   │   ├── impl/                       # Implementações dos use cases
│   │   ├── mapper/                     # Mappers do domínio
│   │   └── model/                      # Modelos de domínio
└── app/                               # Camada de aplicação
    ├── adapter/                        # Adaptadores externos
    │   ├── entrypoint/                 # Pontos de entrada (Controllers)
    │   │   ├── dto/                    # DTOs de entrada
    │   │   └── mapper/                 # Mappers de entrada
    │   └── dataprovider/               # Provedores de dados
    │       ├── dto/                    # DTOs de dados
    │       ├── mapper/                 # Mappers de dados
    │       └── repository/             # Repositórios e entidades
    └── configuration/                  # Configurações da aplicação
        └── exception/                  # Tratamento de exceções
```

## Tecnologias Utilizadas

### Framework Principal
- **Spring Boot 2.7.0** - Framework principal para desenvolvimento da API REST
- **Java 17** - Linguagem de programação

### Persistência e Cache
- **MongoDB** - Banco de dados NoSQL para armazenamento dos dados de parceiros
- **Redis** - Sistema de cache em memória para otimização de consultas geográficas

### Mapeamento e Produtividade
- **MapStruct 1.4.2** - Framework para mapeamento entre DTOs e modelos de domínio
- **Lombok 1.18.22** - Redução de boilerplate code (getters, setters, builders, etc.)

### Geolocalização
- **GeoJSON Jackson** - Suporte para serialização/deserialização de dados GeoJSON
- **JTS (Java Topology Suite)** - Manipulação de dados geoespaciais

### Build e Dependências
- **Maven** - Gerenciamento de dependências e build do projeto

### Testes e Qualidade
- **JUnit 5** - Framework de testes unitários
- **Jacoco** - Análise de cobertura de código
- **ArchUnit** - Testes de arquitetura

### Monitoramento e Observabilidade
- **Spring Boot Actuator** - Endpoints de monitoramento e saúde
- **Micrometer + Prometheus** - Métricas e monitoramento

### Containerização e Deploy
- **Docker** - Containerização da aplicação
- **Docker Compose** - Orquestração dos serviços (app, MongoDB, Redis, Mongo Express)

## Funcionalidades Principais

### 1. Cadastro de Parceiros
**Endpoint:** `POST /ze/v1/parceiros`

Permite o cadastro de novos parceiros no sistema com as seguintes informações:
- **Nome fantasia** (tradingName)
- **Nome do proprietário** (ownerName)
- **Documento/CNPJ** (document)
- **Área de cobertura** (coverageArea) - GeoJSON MultiPolygon
- **Endereço** (address) - GeoJSON Point

#### Exemplo de Payload:
```json
{
  "tradingName": "Adega da Cerveja - Pinheiros",
  "ownerName": "Zé da Silva",
  "document": "1432132123891/0001",
  "coverageArea": { 
    "type": "MultiPolygon",
    "coordinates": [...]
  },
  "address": { 
    "type": "Point",
    "coordinates": [-46.57421, -21.785741]
  }
}
```

### 2. Busca por Identificador
**Endpoint:** `GET /ze/v1/parceiros/{identificador}`

Recupera os dados completos de um parceiro específico através do seu identificador único.

### 3. Busca por Localização
**Endpoint:** `GET /ze/v1/parceiros?longitude={lng}&latitude={lat}`

Encontra parceiros próximos a uma coordenada geográfica específica. Esta funcionalidade:
- Utiliza **consultas geoespaciais** do MongoDB
- Implementa **cache Redis** para otimização de performance
- Retorna parceiros ordenados por proximidade

## Modelos de Dados

### Modelo Principal: ParceiroZe
```java
public class ParceiroZe {
    private String id;                    // Identificador único
    private String tradingName;           // Nome fantasia
    private String ownerName;             // Nome do proprietário  
    private String document;              // CNPJ/Documento
    private CoverageArea coverageArea;    // Área de cobertura (GeoJSON)
    private Address address;              // Endereço (GeoJSON Point)
}
```

### Área de Cobertura
```java
public class CoverageArea {
    private String type;                  // Sempre "MultiPolygon"
    private Double[][][][] coordinates;   // Coordenadas GeoJSON
}
```

### Endereço
```java
public class Address {
    private String type;                  // Sempre "Point"
    private Double[] coordinates;         // [longitude, latitude]
}
```

## Infraestrutura e Deploy

### Docker Compose - Serviços

O projeto utiliza Docker Compose para orchestrar todos os serviços necessários:

```yaml
services:
  mongodb:           # Banco de dados principal
  mongo-seed:        # Inicialização com dados de exemplo
  mongo-express:     # Interface web para MongoDB (porta 8081)
  redis-cache:       # Cache Redis
  application:       # Aplicação Spring Boot (porta 8085)
```

### Inicialização
1. **Build da aplicação:** `mvn clean install`
2. **Subir infraestrutura:** `docker-compose up -d`
3. **Acesso à aplicação:** http://localhost:8085
4. **Interface MongoDB:** http://localhost:8081

## Cache e Performance

### Estratégia de Cache
- **Redis** é utilizado para cache de consultas por coordenadas
- **TTL configurado** para 2 minutos
- **Chave de cache** baseada nas coordenadas de busca
- **Invalidação automática** por expiração

### Configuração Redis
```java
@Configuration
@EnableCaching
public class RedisConfig {
    // Cache manager configurado para 2 minutos
    // Serialização JDK para objetos complexos
    // Suporte SSL/TLS opcional
}
```

## Tratamento de Erros

### Hierarquia de Exceções
- **BusinessException** - Erros de regras de negócio (422)
- **BadRequestException** - Erros de validação (400)
- **InternalServerErrorException** - Erros internos (500)

### Handler Global
```java
@ControllerAdvice
public class GlobalHandlerException {
    // Tratamento centralizado de todas as exceções
    // Respostas padronizadas com códigos HTTP apropriados
    // Logging detalhado para debugging
}
```

## Testes e Qualidade

### Estrutura de Testes
```
src/test/java/
├── br/com/zedelivery/parceiroze/
    ├── core/usecase/impl/              # Testes dos use cases
    ├── app/adapter/entrypoint/         # Testes dos controllers
    ├── app/adapter/dataprovider/       # Testes dos data providers
    └── mocks/                          # Mocks reutilizáveis
```

### Cobertura de Código
- **Jacoco** configurado para análise de cobertura
- **Exclusões definidas** para classes de configuração e DTOs
- **Relatórios HTML** gerados em `target/jacoco-report`

### Testes de Arquitetura
- **ArchUnit** para validação da arquitetura Clean
- **Verificação de dependências** entre camadas
- **Garantia de isolamento** dos módulos

## Monitoramento e Observabilidade

### Actuator Endpoints
- `/actuator/health` - Status de saúde da aplicação
- `/actuator/metrics` - Métricas detalhadas
- `/actuator/prometheus` - Métricas no formato Prometheus

### Logs
- **SLF4J + Logback** para logging estruturado
- **Níveis configuráveis** por ambiente
- **Logs contextuais** com informações de trace

## Consultas Geoespaciais

### MongoDB GeoSpatial
O sistema utiliza as capacidades geoespaciais nativas do MongoDB:

```java
@Query(value = "{'address.coordinates' : {$near:?0}}")
public List<ParceiroZeEntity> findByAddressCoordinatesNear(Double[] lngLat, Pageable pageable);
```

### Índices Geoespaciais
- **Índice 2dsphere** nas coordenadas de endereço
- **Otimização** para consultas de proximidade
- **Suporte** a consultas complexas com GeoJSON

## Segurança e Validação

### Validação de Entrada
- **Bean Validation** (JSR-303) para validação automática
- **Validação customizada** para coordenadas geográficas
- **Sanitização** de entrada de dados

### Headers e Cors
- Configuração adequada para APIs REST
- Suporte a CORS quando necessário
- Headers de segurança apropriados

## Configuração e Ambientes

### Profiles Spring
- **Desenvolvimento:** Configurações locais com Docker
- **Teste:** Configurações otimizadas para testes
- **Produção:** Configurações para ambiente produtivo

### Variáveis de Ambiente
```bash
SPRING_DATA_MONGODB_URI=mongodb://userdb:pass123@mongo:27017/
REDIS_URL=redis-cache
AMBIENTE=dev
```

## Documentação da API

### Collection Postman
Localizada em `./docs/collection-postman/`, contém:
- **Exemplos de requests** para todos os endpoints
- **Casos de teste** com dados válidos
- **Cenários de erro** para validação

### Swagger/OpenAPI
- Documentação automática via Spring Boot
- Definições de schema para todos os DTOs
- Exemplos de request/response

---

## Como Usar Este Projeto

### Pré-requisitos
- Java 17+
- Maven 3.6+
- Docker e Docker Compose

### Passos para Execução
1. **Clone o repositório**
2. **Execute:** `mvn clean install`
3. **Execute:** `docker-compose up -d`
4. **Acesse:** http://localhost:8085/ze/v1/parceiros

### Para Desenvolvimento
1. **Pare o container da aplicação:** `docker-compose stop application`
2. **Execute pela IDE** ou `mvn spring-boot:run`
3. **Use MongoDB e Redis** dos containers

---

Esta documentação fornece uma visão completa do sistema, suas funcionalidades, arquitetura e como utilizá-lo efetivamente.