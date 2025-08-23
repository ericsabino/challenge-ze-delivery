# Guia de Instalação e Deploy

## Pré-requisitos

### Software Necessário
- **Java 17+** - OpenJDK ou Oracle JDK
- **Maven 3.6+** - Para build e gerenciamento de dependências
- **Docker 20.10+** - Para containerização
- **Docker Compose 1.29+** - Para orquestração dos serviços

### Verificação dos Pré-requisitos
```bash
# Verificar Java
java -version

# Verificar Maven  
mvn -version

# Verificar Docker
docker --version

# Verificar Docker Compose
docker-compose --version
```

---

## Instalação Local

### 1. Clone do Repositório
```bash
git clone https://github.com/ericsabino/challenge-ze-delivery.git
cd challenge-ze-delivery
```

### 2. Build da Aplicação
```bash
# Limpar e compilar o projeto
mvn clean compile

# Executar testes
mvn test

# Gerar JAR da aplicação
mvn clean install
```

### 3. Configuração via Docker Compose (Recomendado)

#### Subir todos os serviços
```bash
docker-compose up -d
```

Isso irá subir:
- **MongoDB** (porta 27017)
- **Mongo Express** (porta 8081) - Interface web do MongoDB
- **Redis** (porta 6379) - Cache
- **Aplicação Spring Boot** (porta 8085)
- **Mongo Seed** - Popular dados iniciais

#### Verificar status dos containers
```bash
docker-compose ps
```

#### Ver logs da aplicação
```bash
docker-compose logs -f application
```

### 4. Configuração Manual (Sem Docker)

#### 4.1. MongoDB
```bash
# Instalar e iniciar MongoDB
# Ubuntu/Debian
sudo apt-get install mongodb
sudo systemctl start mongodb

# Mac com Homebrew  
brew install mongodb-community
brew services start mongodb-community
```

#### 4.2. Redis
```bash
# Ubuntu/Debian
sudo apt-get install redis-server
sudo systemctl start redis

# Mac com Homebrew
brew install redis
brew services start redis
```

#### 4.3. Configurar variáveis de ambiente
```bash
export SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/zedelivery
export REDIS_URL=localhost
export REDIS_PORT=6379
export AMBIENTE=dev
```

#### 4.4. Executar aplicação
```bash
mvn spring-boot:run
```

---

## Verificação da Instalação

### 1. Testar Saúde da Aplicação
```bash
curl http://localhost:8085/actuator/health
```

**Resposta esperada:**
```json
{
  "status": "UP"
}
```

### 2. Testar Endpoint da API
```bash
curl http://localhost:8085/ze/v1/parceiros?longitude=-43.93918&latitude=-19.86877
```

### 3. Acessar Mongo Express
Abrir no navegador: http://localhost:8081

---

## Configurações de Ambiente

### Desenvolvimento
```bash
# docker-compose.yml já configurado para desenvolvimento
AMBIENTE=dev
SPRING_DATA_MONGODB_URI=mongodb://userdb:pass123@mongo:27017/
REDIS_URL=redis-cache
```

### Produção
```bash
# Variáveis de ambiente para produção
export AMBIENTE=prod
export SPRING_DATA_MONGODB_URI=mongodb://user:pass@prod-mongo:27017/zedelivery
export REDIS_URL=prod-redis
export REDIS_SSL=true
export REDIS_PORT=6380
```

---

## Deploy em Diferentes Ambientes

### Deploy em Servidor Linux

#### 1. Preparar o servidor
```bash
# Instalar Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

# Instalar Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

#### 2. Deploy da aplicação
```bash
# Copiar projeto para servidor
scp -r . user@server:/opt/ze-delivery/

# Conectar no servidor
ssh user@server

# Navegar para diretório
cd /opt/ze-delivery

# Subir aplicação
docker-compose up -d
```

### Deploy com Kubernetes

#### 1. Criar ConfigMap
```yaml
# k8s/configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: ze-delivery-config
data:
  AMBIENTE: "k8s"
  REDIS_URL: "redis-service"
  SPRING_DATA_MONGODB_URI: "mongodb://mongo-service:27017/zedelivery"
```

#### 2. Deployment da aplicação
```yaml
# k8s/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ze-delivery-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: ze-delivery
  template:
    metadata:
      labels:
        app: ze-delivery
    spec:
      containers:
      - name: ze-delivery
        image: ze-delivery:latest
        ports:
        - containerPort: 8085
        envFrom:
        - configMapRef:
            name: ze-delivery-config
```

### Deploy no AWS ECS

#### 1. Task Definition
```json
{
  "family": "ze-delivery",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "256",
  "memory": "512",
  "containerDefinitions": [
    {
      "name": "ze-delivery-app",
      "image": "your-account.dkr.ecr.region.amazonaws.com/ze-delivery:latest",
      "portMappings": [
        {
          "containerPort": 8085,
          "protocol": "tcp"
        }
      ],
      "environment": [
        {
          "name": "AMBIENTE",
          "value": "aws"
        },
        {
          "name": "SPRING_DATA_MONGODB_URI", 
          "value": "mongodb://documentdb-cluster:27017/zedelivery"
        }
      ]
    }
  ]
}
```

---

## Troubleshooting

### Problemas Comuns

#### 1. Porta já em uso
```bash
# Verificar processos usando porta 8085
lsof -i :8085

# Matar processo se necessário  
kill -9 <PID>
```

#### 2. MongoDB não conecta
```bash
# Verificar se MongoDB está rodando
docker-compose logs mongodb

# Verificar conectividade
docker exec -it mongo mongo --host localhost --port 27017
```

#### 3. Redis não conecta
```bash
# Verificar se Redis está rodando
docker-compose logs redis-cache

# Testar conexão
docker exec -it redis-cache redis-cli ping
```

#### 4. Aplicação não inicia
```bash
# Ver logs detalhados
docker-compose logs -f application

# Verificar recursos disponíveis
docker stats
```

### Logs e Debugging

#### Ver logs da aplicação
```bash
# Via Docker Compose
docker-compose logs -f application

# Via arquivo de log (se configurado)
tail -f logs/application.log
```

#### Habilitar debug no Spring Boot
```bash
# Variável de ambiente
export LOGGING_LEVEL_ROOT=DEBUG

# Ou no application.properties
logging.level.root=DEBUG
logging.level.br.com.zedelivery=TRACE
```

---

## Monitoramento e Saúde

### Endpoints de Monitoramento
```bash
# Saúde da aplicação
curl http://localhost:8085/actuator/health

# Métricas
curl http://localhost:8085/actuator/metrics

# Informações da aplicação
curl http://localhost:8085/actuator/info
```

### Configuração de Alertas

#### Prometheus + Grafana
```yaml
# prometheus.yml
scrape_configs:
  - job_name: 'ze-delivery'
    static_configs:
      - targets: ['localhost:8085']
    metrics_path: '/actuator/prometheus'
```

---

## Backup e Manutenção

### Backup do MongoDB
```bash
# Backup via Docker
docker exec mongo mongodump --host localhost --port 27017 --db zedelivery --out /backup

# Restaurar backup
docker exec mongo mongorestore --host localhost --port 27017 --db zedelivery /backup/zedelivery
```

### Limpeza de Cache Redis
```bash
# Limpar todo o cache
docker exec redis-cache redis-cli FLUSHALL

# Limpar apenas cache específico
docker exec redis-cache redis-cli DEL "parceiroze-por-area-de-cobertura::*"
```

### Atualizações da Aplicação
```bash
# 1. Fazer backup
docker-compose exec mongodb mongodump --out /backup

# 2. Parar aplicação
docker-compose stop application

# 3. Fazer deploy da nova versão
docker-compose build application
docker-compose up -d application

# 4. Verificar saúde
curl http://localhost:8085/actuator/health
```

Este guia cobre todas as formas principais de instalação e deploy do sistema de parceiros Zé Delivery.