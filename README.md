# Arquivei Teste

Teste realizado para a arquivei. Foi feito em Java 8 com Spring Boot, Rabbit, Redis e Docker.

## Iniciando

As instruções abaixo vao te guiar para executar o projeto localmente. Veja a parte de deploy para ver detalher

### Pre-Requisitos

- Java 8
- Docker >= 19

### Executando

Criar as imagens necessarias:
```
docker run -d --name docker-postgres -e POSTGRES_DB=arquiveinfe -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:9.5
```
```
docker run -d --name docker-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```
```
docker run -d --name docker-redis -p 6379:6379 redis:alpine
```
### Entre no diretorio worker

Compile o projeto
```
./mvnw clean package dockerfile:build
```
Execute no container Docker
```
docker run -it --link docker-postgres --link docker-rabbit --link docker-redis -p 8081:8081 -e "SPRING_PROFILES_ACTIVE=docker" emmanuelneri/worker-app
```
Isso vai subir o nosso worker, que é responsável por consumir as mensagens da fila e persistir no banco e inserir no redis.

### Entre no diretorio arquivei

Compile o projeto
```
./mvnw clean package dockerfile:build
```
Execute no container Docker
```
docker run -it --link docker-postgres --link docker-rabbit --link docker-redis -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=docker" emmanuelneri/arquivei-app 
```

Isso vai subir o projeto principal, que vai disponibilizar os endpoints, fazer a primeira consulta a api da Arquivei e adicionar as mensagens na fila para o worker consumir.
- O swagger dos endpoints se encontra em: http://localhost:8080/swagger-ui.html
- endpoint-> /api/nfe/{minha_chave_de_acesso} -> consulta as notas pela chave de acesso
-- ex: (local) GET -> localhost:8080/api/nfe/17171191390526000180550010000282821000324478
- endpoint -> /api/nfe/process -> forca a consulta da api da Arquivei (quando a aplicacao sobe a api da Arquivei jé é consultada)
-- ex: (local) GET -> localhost:8080/api/nfe/process

### Sobre a arquitetura proposta
A aplicacao *arquivei* (dentro do diretorio *arquivei*) é responsável por consumir a api da Arquivei e resgatar as notas fiscais que estão codificadas em base64. Para cada nota codificada (consulta a api da Arquivei até que não tenha mais registros), a aplição descodifica para um XML tradicional e publica uma mensagem referente a nota numa fila do Rabbit; isso foi feito para aumentar a resiliencia da aplicação, uma vez que a publicação é rápida e não depende de banco de dados; também aumenta a escabalabilidade, visto que pode-se escalar sempre que necessário, sem impactar os outros serviços.
Uma vez que as mensagens estão na fila, a aplicação *Worker* (dentro do diretório worker) é responsável por processar as mensagens e persistir no banco de dados e no cache(*Redis*); o cache foi inserido para aumentar a resiliencia da aplicação e também performance; aqui também foi pensado na escalabilidade: pode-se escalar os workers sempre que necessário.
Uma vez que os dados foram persistidos, pode-se consultar o endpoint /api/nfe/{chave_de_acesso}.
O serviço que o endpoint usa sempre consulta o cache antes de chegar ao banco: se a chave de acesso existe no cache, o valor da nota já é retornado, senão, vai até o banco de dados, recuperado o dado, insere no cache e retorna ao usuario.
A arquitetura foi pensada para termos resiliencia e escalabilidade.

### Melhorias que poderiam ocorrer.
- Na aplicação *arquivei*, adiconarmos um circuit breaker que, como fallback, salva as notas em um diretorio. Depois, quando voltar, consome as notas do diretorio e publica na fila.
- No *worker*, salvar no banco e tbm em um sistema de arquivos, de modo que o o nome do arquivo seja a chave de acesso e o conteúdo o valor da nota. Adicionar um circuit breaker na consulta ao banco que, como fallback, pega os dados so sitema de arquivos

