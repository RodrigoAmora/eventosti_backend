# eventosti_backend
Descrição:
----------
Projeto que exibe eventos de TI, feito em Java e Spring Boot.

Dependências:
-------------
Este projeto usa o Java 17 e as seguintes dependências:
* Spring Boot 3.1.5
* Spring Security
* Spring Data JPA
* Devtools
* Thymeleaf
* MySQL

Banco de dados:
---------------
O projeto usa o MySQL.

Gerando o arquivo .jar:
-----------------------
Para gerar o arquivo <b>.jar</b>, execute o comando via terminal no diretório raiz do projeto:
```shell script
mvn clean install
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

Autor:
------
<b>Rodrigo Amora</b>

LinkedIn: https://linkedin.com/in/rodrigoamora <br>
E-mail: rodrigo.amora.freitas@gmail.com

<hr>

Disponível em:
```shell script
http://eventosti.com.br/
```
