# API Rest de CRUD

<p align="center">
  <img src="https://img.shields.io/badge/Java-000?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring-000?style=for-the-badge&logo=spring&logoColor=green"/>
  <img src="https://img.shields.io/badge/PostgreSQL-black?style=for-the-badge&logo=postgresql&logoColor=blue"/>
  <br> <img src="http://img.shields.io/static/v1?label=STATUS&message=OK&color=RED&style=for-the-badge"/>
</p>

## O Desafio

> O desafio da aplicação pode ser visualizado completo em: https://github.com/aikodigital/teste-backend-estagio-v3

Você é o desenvolvedor backend de uma empresa que coleta dados de equipamentos utilizados em uma operação florestal.
Dentre esses dados estão o histórico de posições e estados desses equipamentos. O estado de um equipamento é utilizado
para saber o que o equipamento estava fazendo em um determinado momento, seja Operando, Parado ou em Manutenção. O
estado é alterado de acordo com o uso do equipamento na operação, já a posição do equipamento é coletada através do GPS
e é enviada e armazenada de tempo em tempo pela aplicação.<br>
Seu objetivo é, de posse desses dados, desenvolver uma aplicação backend que exponha esses dados através de uma API.

## Resumo da aplicação

Desenvolvimento de uma API de CRUD utilizando Java + SpringBoot, além do banco de dados PostgreSQL, com documentação da
API realizada no Swagger.

## ⚙️ Como configurar para teste da aplicação
### Método 1 - Utilizando Docker (recomendado) - não requer Java e Gradle instalados em sua máquina:
1. Clone este repositório: ``` git clone https://github.com/apicela/aiko_desafio_backend.git ```
2. Abra o terminal no diretório do arquivo `aiko_desafio_backend/aiko/`.
3. Execute o seguinte comando no terminal: ```docker build -t aiko_apicela . && docker run -p 8080:8080 aiko_apicela```
4. Acesse a interface pelo navegador: http://localhost:8080/swagger-ui/index.html
   <br>
### Método 2 - Utilizando Java 17+ e Gradle 6.4+
1. Clone este repositório: ``` git clone https://github.com/apicela/aiko_desafio_backend.git ```
2. Abra o terminal no diretório do arquivo `aiko_desafio_backend/aiko/`.
3. Execute o seguinte comando no terminal: ```./gradlew build && ./gradlew bootRun```
4. Acesse a interface pelo navegador: http://localhost:8080/swagger-ui/index.html
   <br>


## Visão Geral

### 👨‍💻 Status da aplicação

:heavy_check_mark: Documentação da API<br>
:heavy_check_mark: Documentação da Aplicação <br>
:heavy_check_mark: API do CRUD <br>
:heavy_check_mark: Página Histórico de estados <br>
:heavy_check_mark: Conteinerização <br>
:heavy_check_mark: Mapa de Equipamentos <br>
⏳ Testes (Encontrei uns bugs no teste, removi para manutenção)<br>


### 📋 Pré-requisitos

* Java 17+ <br>
* Gradle 6.4+<br>
### 🛠️ Ferramentas utilizadas

* [SpringBoot](https://spring.io/) - O framework web utilizado
* [Google Maps API JavaScript](https://developers.google.com/maps/documentation/javascript?hl=pt-br) - Utilizado para
  mostrar a localização dos equipamentos no mapa, sendo um mapa interativo.
* [Docker](https://www.docker.com/) - Utilizado para build, teste e deploy da aplicação
* [SwaggerUI](https://swagger.io/tools/swagger-ui/) - Interface utilizada para documentação da API
* [Gradle](https://gradle.org/) - Gerenciador de Dependências
* [Banco de dados H2](https://www.h2database.com/html/main.html) - Utilizado para simular um banco de dados

### Diagrama de classes da aplicação

![image](https://github.com/apicela/aiko_desafio_backend/assets/105384228/6b81eb7f-0b3e-4be8-9de0-4cf461f242d4)


## 📝 Interface da aplicação

![image](https://github.com/apicela/aiko_desafio_backend/assets/105384228/5dd6c534-4a36-4427-b6be-2e1fc8441098)


## 💻 Utilização da API na prática
<br>

[irei adicionar imagem]

<br>

