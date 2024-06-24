# eventosti_backend
Descrição:
----------
Projeto que exibe eventos de TI, feito em Java e Spring Boot.

Endpoints:
----------
A documentação dos endpoints da API pode ser vista através do Swagger e do Redoc.<br>

Para consultar a documentação dos endpoints através do Swagger: `<URL_DO_PORJETO>/swagger-ui.html` <br>
Para consultar a documentação dos endpoints através do Redoc: `<URL_DO_PORJETO>/redoc.html` <br>

Caso prefira usar o Postman, na pasta collections tem o arquivo com a collection para usar os endpoints via Postman.

Dependências:
-------------
Este projeto usa o Java 17 e as seguintes dependências:
* Spring Boot 3.1.5
* Spring Security
* Swagger
* OpenAPI
* JPA
* Devtools
* jUnit
* Rest-Assured

Banco de dados:
---------------
O projeto usa o MySQL para os ambientes de desenvolvimento e produção e o H2 para o ambiente de teste.<br>
Para acessar o painel do H2: `<URL_DO_PORJETO>/h2-console`

Testes:
-------
O projeto possui testes de API.

Gerando o arquivo .jar:
-----------------------
Para gerar o arquivo <b>.jar</b>, execute o comando via terminal no diretório raiz do projeto:
```shell script
mvn clean install
```

Docker:
-------
Para gerar os containers do Docker, execute os comandos via terminal no diretório raiz do projeto: 
```shell script
docker-compose build

docker-compose up -d
```

Rodando o projeto:
------------------
Para iniciar a aplicação localmente via IDE, execute a classe `EventostiApplication`

Para iniciar a aplicação localmente via terminal, execute o comando via terminal no diretório raiz do projeto:
```shell script
mvn spring-boot:run
```

Autor:
------
<b>Rodrigo Amora</b>

LinkedIn: https://linkedin.com/in/rodrigoamora <br>
E-mail: rodrigo.amora.freitas@gmail.com

<hr>
