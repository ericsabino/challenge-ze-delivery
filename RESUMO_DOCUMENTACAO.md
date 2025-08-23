# README - Documentação do Repositório

Este documento fornece uma visão geral completa do repositório **challenge-ze-delivery** e sua documentação.

## 📋 Índice da Documentação

| Documento | Descrição | Conteúdo |
|-----------|-----------|----------|
| [DOCUMENTACAO_COMPLETA.md](./DOCUMENTACAO_COMPLETA.md) | Documentação abrangente do sistema | Visão geral, arquitetura, tecnologias, funcionalidades, modelos de dados, infraestrutura |
| [ANALISE_TECNICA.md](./ANALISE_TECNICA.md) | Análise detalhada do código e padrões | Estrutura do código, padrões implementados, análise de cada camada, princípios SOLID |
| [API_DOCUMENTATION.md](./API_DOCUMENTATION.md) | Documentação completa da API REST | Endpoints, payloads, responses, códigos de erro, exemplos de uso |
| [GUIA_INSTALACAO.md](./GUIA_INSTALACAO.md) | Guia passo-a-passo para setup | Instalação local, deploy, configuração de ambientes, troubleshooting |

## 🎯 O Que Este Repositório Faz

### Resumo Executivo
O **Sistema de Gestão de Parceiros Zé Delivery** é uma API REST desenvolvida para gerenciar parceiros de entrega, permitindo:

- ✅ **Cadastro de parceiros** com áreas de cobertura geográficas
- ✅ **Busca geolocalizada** de parceiros próximos ao cliente  
- ✅ **Consulta individual** de parceiros por ID
- ✅ **Cache inteligente** para otimização de performance
- ✅ **Arquitetura limpa** e bem estruturada

### Principais Características Técnicas
- **Clean Architecture** com separação clara de responsabilidades
- **Spring Boot 2.7** com Java 17
- **MongoDB** para persistência geoespacial
- **Redis** para cache de consultas
- **Docker Compose** para ambiente completo
- **GeoJSON** para dados geográficos
- **Testes abrangentes** com JUnit e Jacoco
- **Monitoramento** via Actuator e Prometheus

## 🏗️ Arquitetura Resumida

```
┌─────────────────────────────────────────┐
│             CONTROLLERS                 │
│        (Pontos de Entrada)              │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│             USE CASES                   │
│          (Regras de Negócio)            │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│             GATEWAYS                    │
│         (Contratos/Interfaces)          │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          DATA PROVIDERS                 │
│     (MongoDB, Redis, Externos)          │
└─────────────────────────────────────────┘
```

## 🚀 Quick Start

### 1. Pré-requisitos
```bash
# Verificar instalações
java -version    # Java 17+
mvn -version     # Maven 3.6+
docker --version # Docker 20.10+
```

### 2. Executar
```bash
# 1. Clonar repositório
git clone https://github.com/ericsabino/challenge-ze-delivery.git
cd challenge-ze-delivery

# 2. Build da aplicação
mvn clean install

# 3. Subir infraestrutura completa
docker-compose up -d

# 4. Verificar saúde
curl http://localhost:8085/actuator/health
```

### 3. Testar API
```bash
# Buscar parceiros próximos
curl "http://localhost:8085/ze/v1/parceiros?longitude=-43.93918&latitude=-19.86877"
```

## 📊 Tecnologias por Categoria

### Backend & Framework
- Spring Boot 2.7.0
- Java 17
- Maven

### Persistência & Cache
- MongoDB (dados geoespaciais)
- Redis (cache)

### Mapeamento & Produtividade
- MapStruct (mapeamento)
- Lombok (redução boilerplate)

### Testes & Qualidade
- JUnit 5
- Jacoco (cobertura)
- ArchUnit (arquitetura)

### Deploy & Infraestrutura
- Docker & Docker Compose
- Spring Boot Actuator
- Prometheus (métricas)

## 🔗 API Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/ze/v1/parceiros` | Cadastra novo parceiro |
| `GET` | `/ze/v1/parceiros/{id}` | Busca parceiro por ID |
| `GET` | `/ze/v1/parceiros?lng={}&lat={}` | Busca por localização |

## 📁 Estrutura de Diretórios Principais

```
├── src/main/java/br/com/zedelivery/parceiroze/
│   ├── core/                    # Domínio e regras de negócio
│   │   ├── usecase/            # Casos de uso
│   │   ├── gateway/            # Interfaces
│   │   └── model/              # Modelos de domínio
│   └── app/                    # Camada de aplicação
│       ├── adapter/            # Adaptadores
│       │   ├── entrypoint/     # Controllers REST
│       │   └── dataprovider/   # Repositórios e dados
│       └── configuration/      # Configurações
├── docs/                       # Documentação adicional
├── mongo-seed/                 # Dados iniciais MongoDB
└── docker-compose.yml          # Orquestração dos serviços
```

## 🧪 Executar Testes

```bash
# Todos os testes
mvn test

# Com relatório de cobertura
mvn test jacoco:report

# Ver relatório HTML
open target/site/jacoco/index.html
```

## 🔧 Configuração por Ambiente

### Desenvolvimento (Default)
```bash
docker-compose up -d
```

### Produção
```bash
export SPRING_DATA_MONGODB_URI=mongodb://prod-host:27017/zedelivery
export REDIS_URL=prod-redis-host
export AMBIENTE=prod
java -jar target/parceiroze-0.0.1-SNAPSHOT.jar
```

## 📈 Monitoramento

### Endpoints de Saúde
- `/actuator/health` - Status da aplicação
- `/actuator/metrics` - Métricas detalhadas  
- `/actuator/prometheus` - Métricas Prometheus

### Interfaces Web
- **Aplicação:** http://localhost:8085
- **Mongo Express:** http://localhost:8081

## 🤝 Como Contribuir

1. **Fork** o repositório
2. **Crie** uma branch para sua feature
3. **Faça** os testes passarem
4. **Mantenha** a cobertura de código
5. **Siga** os padrões de Clean Architecture
6. **Submeta** um Pull Request

## 📚 Leitura Adicional

Para informações mais detalhadas, consulte os documentos específicos:

- **[DOCUMENTACAO_COMPLETA.md](./DOCUMENTACAO_COMPLETA.md)** - Para entender completamente o sistema
- **[ANALISE_TECNICA.md](./ANALISE_TECNICA.md)** - Para análise profunda do código
- **[API_DOCUMENTATION.md](./API_DOCUMENTATION.md)** - Para integração com a API
- **[GUIA_INSTALACAO.md](./GUIA_INSTALACAO.md)** - Para setup e deploy

## 🏷️ Versões e Compatibilidade

| Componente | Versão | Compatibilidade |
|------------|--------|----------------|
| Java | 17+ | OpenJDK ou Oracle |
| Spring Boot | 2.7.0 | Spring 5.x |
| MongoDB | 4.4+ | Suporte GeoSpatial |
| Redis | 6.0+ | Cache distribuído |
| Maven | 3.6+ | Build tool |
| Docker | 20.10+ | Containerização |

## 📞 Suporte

Para questões técnicas ou problemas:

1. **Consulte** o [GUIA_INSTALACAO.md](./GUIA_INSTALACAO.md) para troubleshooting
2. **Verifique** os logs da aplicação: `docker-compose logs -f application`
3. **Teste** os endpoints de saúde: `/actuator/health`

---

**Desenvolvido como desafio backend para Zé Delivery** 🍺

Este README serve como ponto de entrada para toda a documentação do sistema. Para informações detalhadas sobre qualquer aspecto específico, consulte os documentos correspondentes listados acima.