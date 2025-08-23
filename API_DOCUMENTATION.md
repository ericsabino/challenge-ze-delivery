# Documentação da API - Parceiros Zé Delivery

## Base URL
```
Local: http://localhost:8085
```

## Endpoints Disponíveis

### 1. Cadastrar Parceiro
**Endpoint:** `POST /ze/v1/parceiros`

**Descrição:** Cadastra um novo parceiro no sistema com área de cobertura e localização.

#### Request Body
```json
{
  "tradingName": "string",          // Nome fantasia do estabelecimento
  "ownerName": "string",            // Nome do proprietário
  "document": "string",             // CNPJ do estabelecimento  
  "coverageArea": {                 // Área de cobertura (GeoJSON MultiPolygon)
    "type": "MultiPolygon",
    "coordinates": [
      [
        [
          [longitude, latitude],    // Array de coordenadas definindo polígonos
          [longitude, latitude],
          // ... mais coordenadas
          [longitude, latitude]     // Último ponto deve ser igual ao primeiro (polígono fechado)
        ]
      ]
    ]
  },
  "address": {                      // Localização do estabelecimento (GeoJSON Point)
    "type": "Point", 
    "coordinates": [longitude, latitude]
  }
}
```

#### Exemplo de Request
```json
{
  "tradingName": "Adega da Cerveja - Pinheiros",
  "ownerName": "Zé da Silva", 
  "document": "1432132123891/0001",
  "coverageArea": {
    "type": "MultiPolygon",
    "coordinates": [
      [
        [
          [102.0, 2.0],
          [103.0, 2.0], 
          [103.0, 3.0],
          [102.0, 3.0],
          [102.0, 2.0]
        ]
      ],
      [
        [
          [100.0, 0.0],
          [101.0, 0.0],
          [101.0, 1.0], 
          [100.0, 1.0],
          [100.0, 0.0]
        ],
        [
          [100.2, 0.2],
          [100.8, 0.2],
          [100.8, 0.8],
          [100.2, 0.8], 
          [100.2, 0.2]
        ]
      ]
    ]
  },
  "address": {
    "type": "Point",
    "coordinates": [-46.57421, -21.785741]
  }
}
```

#### Response
**Status:** `201 Created`
```json
// Corpo vazio - apenas status de criação
```

#### Possíveis Erros
- **400 Bad Request:** Dados de entrada inválidos
- **422 Unprocessable Entity:** Violação de regras de negócio
- **500 Internal Server Error:** Erro interno do servidor

---

### 2. Buscar Parceiro por ID
**Endpoint:** `GET /ze/v1/parceiros/{identificador}`

**Descrição:** Recupera os dados completos de um parceiro específico através do seu identificador único.

#### Path Parameters
- `identificador` (string, obrigatório): ID único do parceiro

#### Response
**Status:** `200 OK`
```json
{
  "id": "62bf4747925c8527b901a53e",
  "tradingName": "Bar Nem Tanto",
  "ownerName": "Lucas Satto",
  "document": "06.269.410/0001-19", 
  "coverageArea": {
    "type": "MultiPolygon",
    "coordinates": [
      [
        [
          [-43.93918, -19.86877],
          [-43.93462, -19.86751],
          [-43.92989, -19.86586],
          [-43.93463, -19.85896],
          // ... mais coordenadas
          [-43.93918, -19.86877]
        ]
      ]
    ]
  },
  "address": {
    "type": "Point",
    "coordinates": [-43.97662, -19.837042]
  }
}
```

#### Possíveis Erros
- **400 Bad Request:** Identificador inválido ou ausente
- **404 Not Found:** Parceiro não encontrado
- **500 Internal Server Error:** Erro interno do servidor

---

### 3. Buscar Parceiros por Localização
**Endpoint:** `GET /ze/v1/parceiros?longitude={lng}&latitude={lat}`

**Descrição:** Encontra parceiros próximos a uma coordenada geográfica específica. Utiliza cache Redis para otimização.

#### Query Parameters
- `longitude` (number, obrigatório): Longitude da localização do cliente
- `latitude` (number, obrigatório): Latitude da localização do cliente

#### Exemplo de Request
```
GET /ze/v1/parceiros?longitude=-43.93918&latitude=-19.86877
```

#### Response  
**Status:** `200 OK`
```json
[
  {
    "id": "62c5b3ba833ce995208c9f9f",
    "tradingName": "Bar Nem Tanto", 
    "ownerName": "Lucas Satto",
    "document": "06.269.410/0001-19",
    "coverageArea": {
      "type": "MultiPolygon",
      "coordinates": [
        [
          [
            [-43.93918, -19.86877],
            [-43.93462, -19.86751],
            [-43.92989, -19.86586],
            // ... coordenadas da área de cobertura
            [-43.93918, -19.86877]
          ]
        ]
      ]
    },
    "address": {
      "type": "Point",
      "coordinates": [-43.97662, -19.837042]
    }
  }
  // ... outros parceiros próximos
]
```

#### Comportamento da Busca
- **Ordenação:** Parceiros retornados por proximidade (mais próximos primeiro)
- **Cache:** Resultados ficam em cache por 2 minutos
- **Limite:** Configurável via paginação (padrão: sem limite específico)

#### Possíveis Erros
- **400 Bad Request:** Coordenadas inválidas ou ausentes
- **500 Internal Server Error:** Erro interno do servidor

---

## Modelos de Dados

### ParceiroDto
```typescript
interface ParceiroDto {
  id?: string;                    // Gerado automaticamente no cadastro
  tradingName: string;            // Nome fantasia - obrigatório
  ownerName: string;              // Nome do proprietário - obrigatório  
  document: string;               // CNPJ/documento - obrigatório
  coverageArea: CoverageAreaDto;  // Área de cobertura - obrigatório
  address: AddressDto;            // Endereço - obrigatório
}
```

### CoverageAreaDto (GeoJSON MultiPolygon)
```typescript
interface CoverageAreaDto {
  type: "MultiPolygon";           // Sempre "MultiPolygon"
  coordinates: number[][][][];    // Array 4D de coordenadas
}
```

**Estrutura das Coordenadas:**
- **Nível 1:** Array de polígonos
- **Nível 2:** Array de anéis (exterior + buracos internos)
- **Nível 3:** Array de pontos do anel
- **Nível 4:** Par [longitude, latitude]

### AddressDto (GeoJSON Point) 
```typescript
interface AddressDto {
  type: "Point";                  // Sempre "Point"
  coordinates: [number, number];  // [longitude, latitude]
}
```

---

## Códigos de Status HTTP

| Status | Significado | Uso |
|--------|-------------|-----|
| 200 | OK | Busca realizada com sucesso |
| 201 | Created | Parceiro cadastrado com sucesso |
| 400 | Bad Request | Dados de entrada inválidos ou ausentes |
| 404 | Not Found | Parceiro não encontrado |
| 422 | Unprocessable Entity | Violação de regras de negócio |
| 500 | Internal Server Error | Erro interno do sistema |

---

## Estrutura de Respostas de Erro

### Erro de Validação (400)
```json
{
  "codigo": "400",
  "mensagem": "Dados de entrada inválidos",
  "campos": [
    {
      "campo": "longitude",
      "mensagem": "Longitude não pode ser nulo"
    },
    {
      "campo": "tradingName", 
      "mensagem": "Nome fantasia é obrigatório"
    }
  ]
}
```

### Erro de Negócio (422)
```json
{
  "codigo": "422",
  "message": "Regra de negócio violada",
  "detalhes": [
    {
      "codigo": "DUPLICATE_DOCUMENT",
      "mensagem": "CNPJ já cadastrado no sistema"
    }
  ]
}
```

### Erro Interno (500)
```json
{
  "codigo": "500",
  "mensagem": "Erro interno do servidor",
  "timestamp": "2023-07-01T10:30:00Z"
}
```

---

## Headers HTTP

### Request Headers
```http
Content-Type: application/json
Accept: application/json
```

### Response Headers
```http
Content-Type: application/json;charset=UTF-8
Cache-Control: no-cache (para endpoints de escrita)
Cache-Control: max-age=120 (para endpoints de leitura com cache)
```

---

## Exemplos de Uso com cURL

### Cadastrar Parceiro
```bash
curl -X POST http://localhost:8085/ze/v1/parceiros \
  -H "Content-Type: application/json" \
  -d '{
    "tradingName": "Adega da Cerveja",
    "ownerName": "João Silva",
    "document": "12345678000199", 
    "coverageArea": {
      "type": "MultiPolygon",
      "coordinates": [[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]]]]
    },
    "address": {
      "type": "Point",
      "coordinates": [-46.57421, -21.785741]
    }
  }'
```

### Buscar Parceiro por ID
```bash
curl -X GET http://localhost:8085/ze/v1/parceiros/62bf4747925c8527b901a53e \
  -H "Accept: application/json"
```

### Buscar Parceiros por Localização  
```bash
curl -X GET "http://localhost:8085/ze/v1/parceiros?longitude=-43.93918&latitude=-19.86877" \
  -H "Accept: application/json"
```

---

## Performance e Cache

### Cache Redis
- **Endpoint Afetado:** `GET /ze/v1/parceiros?longitude={lng}&latitude={lat}`
- **TTL:** 2 minutos
- **Chave de Cache:** Baseada nas coordenadas de busca
- **Estratégia:** Cache-Aside (gerenciado pela aplicação)

### Otimizações Implementadas
- **Índices Geoespaciais:** MongoDB com índice 2dsphere
- **Paginação:** Suporte nativo do Spring Data
- **Connection Pooling:** Para MongoDB e Redis
- **Serialização Otimizada:** Para objetos em cache

---

## Monitoramento

### Endpoints de Saúde
```
GET /actuator/health       # Status geral da aplicação
GET /actuator/metrics      # Métricas detalhadas
GET /actuator/prometheus   # Métricas para Prometheus
```

### Logs de Auditoria
Todas as operações são logadas com:
- Timestamp da operação
- Usuário/IP (quando disponível)
- Parâmetros da requisição
- Tempo de resposta
- Status da operação

Esta documentação cobre completamente a API REST do sistema de parceiros Zé Delivery.