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

Localizado no projeto em: /docs/collection-postman

## Como Utilizar

Na raiz do projeto contém um arquivo [docker-compose] responsável por prover a infra necessária para aplicação rodar.

Partindo do princípio que já tem o docker instalado na máquina. No diretório do projeto onde se encontra o arquivo [docker-compose] executar no terminal:
* docker-compose up -d

Nele você encontra:
* Mongodb - Sobe a imagem do mongo
* Mongo-seed - Faz o import do arquivo pdvs.json para a base
* Mongo-express - Uma interface amigavel que sobe na porta 8181
* Redis - Está sendo utilizado como cache

Ao executar este comando você deve obter algo como a imagem abaixo:
<img src="/docs/docker.png" alt="Docker orquestrando os containers" />

Agora basta subir a aplicação que estará disponivel para utilização.

## Arquitetura - Clean Arch

<img src="/docs/Clean-Arch.png" alt="Clean Arch"/>
