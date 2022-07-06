# challenge-ze-delivery
Desafio backend proposto pelo Zé Delivery

# Tecnologias

**Maven** - Para gerenciar dependência.

**Lombok** - Para produtividade, ele fica responsável por gerar os Getter e Setter; Builder; Construtor, entre outros.

**MapStruct** - Para fazer o mapper entre meu DTO e minha Classe Modelo.

**Spring boot** - Com configurações rápidas, você consegue, disponibilizar uma aplicação baseada no Spring. No caso disponibilizamos uma API.

**Redis** - Utilizado para cache quando consultado parceiros dada uma coordenada

**MongoDB** - Armazenamento dos dados dos parceiros

**Log4J** - Para gerenciar os logs da aplicação

**Junit** - Para testes Unitários

**Jacoco** - Para cobertura de teste

**Prometheus** - Para monitorar a saúde da aplicação através de métricas

**Arquitetura de Software** - Clean Architecture

## Collection Postman

Localizado no projeto em: [Collection](./docs/collection-postman)

## Como Utilizar

Com o projeto na máquina, através de um terminal, navegar até a pasta onde se localiza o projeto e executar o comando abaixo para gerar o .jar ou através da sua IDE preferida rodar o Maven install:

* mvn clean install

Na raiz do projeto contém um arquivo [docker-compose] responsável por prover a infra necessária para aplicação rodar.

Partindo do princípio que já tem o docker instalado na máquina. No diretório do projeto onde se encontra o arquivo [docker-compose] executar no terminal:
* docker-compose up -d

Nele você encontra:
* Mongodb - Sobe a imagem do mongo
* Mongo-seed - Faz o import do arquivo pdvs.json para a base
* Mongo-express - Uma interface amigavel que sobe na porta 8181
* Redis - Está sendo utilizado como cache
* ze-delivery-app - Sobe a aplicação disponibilizando na porta 8085

Ao executar este comando você deve obter algo como a imagem abaixo:
<img src="/docs/docker.png" alt="Docker orquestrando os containers" />

Nesse ponto os Endpoints já estarão disponíveis para consumo. Caso queira debugar a aplicação, basta pausar a aplicação no doocker e executar na IDE.

## Arquitetura - Clean Arch

Esta é o desenho de arquitetura utilizado para desenvolvimento deste projeto.

<img src="/docs/Clean-Arch.png" alt="Clean Arch"/>

# Serviços Zé Delivery

Temos 3 API's no serviço <i>ze-delivery-app</i> 

| **Método**         | **Endpoint**                                    | **Descrição**                                               |
| -------------------|-------------------------------------------------|-------------------------------------------------------------|
| **Post**           | /ze/v1/parceiros                                | Cadastra um Parceiro Zé na base de dados MongoDB            |
| **Get**            | /ze/v1/parceiros/{identificador}                | Busca um Parceiro Zé pelo identificador                     |
| **Get**            | /ze/v1/parceiros?longitude={lgn}&latitude={ltd} | Busca um Parceiro Zé dado a longitude e latitude do cliente |         |

### Post Parceiro Zé

Endpoint para chamada

```
Local
http://localhost:8085/ze/v1/parceiros
```
Body de exemplo para cadastro de parceiro:

```json5
{
  "tradingName": "Adega da Cerveja - Pinheiros",
  "ownerName": "Zé da Silva",
  "document": "1432132123891/0001",
  "coverageArea": { 
    "type": "MultiPolygon",
    "coordinates": [    [[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]],    
                        [[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],     
                         [[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]    
                         ]
  },
  "address": { 
    "type": "Point",
    "coordinates": [-46.57421, -21.785741]
  }
}
```

#### Response
```
Status: 201 Created
```

### Get Parceiro Zé por Identificador

Endpoint para chamada

```
Local
http://localhost:8085/ze/v1/parceiros/{identificador}
```

#### Response

```json5
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
          [
            -43.93918,
            -19.86877
          ],
          [
            -43.93462,
            -19.86751
          ],
          [
            -43.92989,
            -19.86586
          ],
          [
            -43.93463,
            -19.85896
          ],
          [
            -43.93544,
            -19.85711
          ],
          [
            -43.93523,
            -19.85598
          ],
          [
            -43.93613,
            -19.85486
          ],
          [
            -43.93613,
            -19.85482
          ],
          [
            -43.93615,
            -19.85502
          ],
          [
            -43.93595,
            -19.85514
          ],
          [
            -43.93598,
            -19.85562
          ],
          [
            -43.93557,
            -19.85225
          ],
          [
            -43.9357,
            -19.85345
          ],
          [
            -43.93523,
            -19.85206
          ],
          [
            -43.9337,
            -19.8502
          ],
          [
            -43.93304,
            -19.84768
          ],
          [
            -43.93346,
            -19.84467
          ],
          [
            -43.9356,
            -19.84139
          ],
          [
            -43.93748,
            -19.83908
          ],
          [
            -43.938,
            -19.83863
          ],
          [
            -43.93849,
            -19.83838
          ],
          [
            -43.93915,
            -19.8377
          ],
          [
            -43.94054,
            -19.83476
          ],
          [
            -43.94083,
            -19.83269
          ],
          [
            -43.94159,
            -19.8309
          ],
          [
            -43.94379,
            -19.82638
          ],
          [
            -43.94586,
            -19.82163
          ],
          [
            -43.9498,
            -19.81362
          ],
          [
            -43.95158,
            -19.80659
          ],
          [
            -43.97759,
            -19.80085
          ],
          [
            -44.00359,
            -19.80287
          ],
          [
            -44.01354,
            -19.82161
          ],
          [
            -44.01458,
            -19.85648
          ],
          [
            -44.00569,
            -19.86225
          ],
          [
            -43.99123,
            -19.87779
          ],
          [
            -43.97657,
            -19.88014
          ],
          [
            -43.96242,
            -19.87814
          ],
          [
            -43.95292,
            -19.87739
          ],
          [
            -43.95022,
            -19.87477
          ],
          [
            -43.94938,
            -19.87218
          ],
          [
            -43.93918,
            -19.86877
          ]
        ]
      ]
    ]
  },
  "address": {
    "type": "Point",
    "coordinates": [
      -43.97662,
      -19.837042
    ]
  }
}
```
### Get Parceiro Zé por Coordenadas do cliente

Endpoint para chamada

```
Local
http://localhost:8085/ze/v1/parceiros?longitude={Lng}&latitude={Ltd}

Ex.: 
    longitude=-43.93918
    latitude=-19.86877
```
#### Response

```json5
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
          [
            -43.93918,
            -19.86877
          ],
          [
            -43.93462,
            -19.86751
          ],
          [
            -43.92989,
            -19.86586
          ],
          [
            -43.93463,
            -19.85896
          ],
          [
            -43.93544,
            -19.85711
          ],
          [
            -43.93523,
            -19.85598
          ],
          [
            -43.93613,
            -19.85486
          ],
          [
            -43.93613,
            -19.85482
          ],
          [
            -43.93615,
            -19.85502
          ],
          [
            -43.93595,
            -19.85514
          ],
          [
            -43.93598,
            -19.85562
          ],
          [
            -43.93557,
            -19.85225
          ],
          [
            -43.9357,
            -19.85345
          ],
          [
            -43.93523,
            -19.85206
          ],
          [
            -43.9337,
            -19.8502
          ],
          [
            -43.93304,
            -19.84768
          ],
          [
            -43.93346,
            -19.84467
          ],
          [
            -43.9356,
            -19.84139
          ],
          [
            -43.93748,
            -19.83908
          ],
          [
            -43.938,
            -19.83863
          ],
          [
            -43.93849,
            -19.83838
          ],
          [
            -43.93915,
            -19.8377
          ],
          [
            -43.94054,
            -19.83476
          ],
          [
            -43.94083,
            -19.83269
          ],
          [
            -43.94159,
            -19.8309
          ],
          [
            -43.94379,
            -19.82638
          ],
          [
            -43.94586,
            -19.82163
          ],
          [
            -43.9498,
            -19.81362
          ],
          [
            -43.95158,
            -19.80659
          ],
          [
            -43.97759,
            -19.80085
          ],
          [
            -44.00359,
            -19.80287
          ],
          [
            -44.01354,
            -19.82161
          ],
          [
            -44.01458,
            -19.85648
          ],
          [
            -44.00569,
            -19.86225
          ],
          [
            -43.99123,
            -19.87779
          ],
          [
            -43.97657,
            -19.88014
          ],
          [
            -43.96242,
            -19.87814
          ],
          [
            -43.95292,
            -19.87739
          ],
          [
            -43.95022,
            -19.87477
          ],
          [
            -43.94938,
            -19.87218
          ],
          [
            -43.93918,
            -19.86877
          ]
        ]
      ]
    ]
  },
  "address": {
    "type": "Point",
    "coordinates": [
      -43.97662,
      -19.837042
    ]
  }
}
```

