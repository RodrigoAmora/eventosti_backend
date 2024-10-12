# eventosti_backend
Descrição:
----------
Projeto para divulgação de eventos de tecnologia, feito em Java e Spring Boot e hospedado no AWS.

Dependências:
-------------
Este projeto usa o Java 17 e as seguintes dependências:
* Spring Boot 3.1.5
* Spring Security
* Spring Data JPA
* Devtools
* Thymeleaf
* Actuator
* Micrometer
* Swagger
* OpenAPI
* MySQL
* H2
* jUnit

Endpoints:
----------
A documentação dos endpoints pode ser vista através do Swagger e do Redoc.<br>

<b>Documentação dos endpoints via Swagger:</b>
```shell script
http://localhost:8080/swagger-ui.html
```

<b>Documentação dos endpoints via Redoc:</b>
```shell script
http://localhost:8080/redoc.html
```

##
Na pasta <b>`Postman`</b> contém a collection para usar os endpoints via Postman.

Banco de dados:
---------------
O projeto usa o MySQL para os ambientes de desenvolvimento e produção e o H2 para os testes.<br>

##
Para acessar o painel do H2:
```shell script
http://localhost:8080/h2-console
```

<b>OBS:</b> Para acessar o painel do H2 é preciso rodar a aplicação com properties de test.

Gerando o arquivo .jar:
-----------------------
Para gerar o arquivo <b>.jar</b>, execute o comando via terminal no diretório raiz do projeto:
```shell script
mvn clean install -P{profile} -DskipTests
```

Rodando os testes:
------------------
Para rodar os testes, exceute o comando:
```shell script
mvn test
```

Rodando o projeto:
------------------
Para iniciar a aplicação localmente via IDE, execute a classe `EventostiApplication`

Para iniciar a aplicação localmente via terminal, execute o comando no diretório raiz do projeto:
```shell script
mvn spring-boot:run
```

Docker:
-------
Para rodar o projeto em um container Docker, primeiro deve-se gerar o .jar do projeto.<br>
Após isso, deve-se gerar o build e subir os containers do Docker.<br><br>
<b>Fazendo o build dos containers do Docker:</b>
```shell script
docker-compose build

```

<b>Subindo os containers do Docker:</b>
```shell script
docker-compose up -d
```

##
Para automatizar esse processo, basta executar o Shellscript <b>`docker_build_and_run.sh`</b> na raiz do projeto:
```shell script
./docker_build_and_run.sh
```

Prometheus:
-----------
Acesse o Prometheus através do endereço:
```shell script
http://localhost:9090/
```

Grafana:
--------
Acesse o Grafana através do endereço:
```shell script
http://localhost:3000/
```

<u><b>Usuário padrão</b></u> <br>
<b>Username:</b> admin <br>
<b>Password:</b> admin

Acesso ao projeto:
------------------
O projeto está disponível através do endereço:<br>
http://eventosti.com.br

Autor:
------
<b>Rodrigo Amora</b>

LinkedIn: https://linkedin.com/in/rodrigoamora <br>
E-mail: rodrigo.amora.freitas@gmail.com
