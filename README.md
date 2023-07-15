# API Rest de CRUD

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Google_Cloud-4285F4?style=for-the-badge&logo=google-cloud&logoColor=white"><br>
   <img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=RED&style=for-the-badge"/>
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

## Visão Geral

### 👨‍💻 Status da aplicação

:heavy_check_mark: Documentação da API<br>
:heavy_check_mark: Documentação da Aplicação <br>
:heavy_check_mark: API do CRUD <br>
:heavy_check_mark: Página Histórico de estados <br>
:heavy_check_mark: Conteinerização <br>
:heavy_check_mark: Mapa de Equipamentos <br>
:heavy_check_mark: Testes <br>

### 📋 Pré-requisitos

* Java <br>
* Docker<br>

### 🛠️ Ferramentas utilizadas

* [SpringBoot](https://spring.io/) - O framework web utilizado
* [Google Maps API JavaScript](https://developers.google.com/maps/documentation/javascript?hl=pt-br) - Utilizado para
  mostrar a localização dos equipamentos no mapa,
  sendo um mapa interativo.
* [Docker](https://www.docker.com/) - Utilizado para build, teste e deploy da aplicação
* [SwaggerUI](https://swagger.io/tools/swagger-ui/) - Interface utilizada para documentação da API
* [Maven](https://maven.apache.org/) - Gerente de Dependência

### 💻 Por dentro da aplicação

<p align="center">
<img src="https://github.com/aikodigital/teste-backend-estagio-v3/blob/master/img/diagram.png"/>
</p>

O primeiro passo é compreendermos
a [UML](https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-uml/), podemos interpretar que, os
diamantes negros indicam uma relação
de [composição](https://stackoverflow.com/questions/885937/what-is-the-difference-between-association-aggregation-and-composition),
sendo a parte "owner" da relação, enquanto os círculos cinzas, são as partes "owned".

Após compreendido o [relacionamento](https://docs.oracle.com/cd/A97688_16/generic.903/a97677/ormap.htm), é necessário
definir como será organizado os componentes das entidades.
<br><br>

**As entidades foram subdivididas em 5 partes:**<br>
Por padrão, para facilitar a manutenção do código, as entidades são divididas entre:
<br><br>

* [Model](https://pt.wikipedia.org/wiki/Plain_Old_Java_Objects)
  <br>Popularmente conhecido como Java Bean, Na arquitetura MVC, representa o domínio da entidade```(@Entity)```, é uma
  classe em Java onde conterá todos os atributos presentes da entidade(constructors, getters, setters, etc). É a classe
  a
  ser [serializada](https://www.javatpoint.com/serialization-in-java#:~:text=Serializable%20is%20a%20marker%20interface,object%20needs%20to%20be%20persisted).
  <br>

* [DTO](https://en.wikipedia.org/wiki/Data_transfer_object)
  <br>É a classe que será utilizada para a transferência de dados do objeto referente a entidade. Neste exemplo, nas
  classes "owner", suas ID's são criadas automáticamente por um gerador UUID. Supondo a classe 'EquipmentModel', ela
  possui os atributos {id, name}, como o id é gerado automaticamente, não faz sentido passarmos para o usuário uma
  criação de novo objeto EquipmentModel tendo que preencher os valores de id e name, portanto, é criado uma classe
  EquipmentModelDTO, apenas como parâmetro o atributo 'name', se tornando uma classe 'facilitadora'.
  <br>

* [Repository](https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html)
  <br>É a classe que implementa o JPA Repository(Java Persistence API), sendo um mecanismo para encapsular o banco,
  recuperar e buscar comportamento que simulam uma coleção de objetos, basicamente é a parte em que faz a intermediação
  dos dados da entidade e o banco de dados.
  <br>

* [Service](https://www.educba.com/spring-boot-service/)
  <br>É responsável pela lógica de negócio da sua aplicação, além de ser responsável por se comunicar com as camadas
  mais internas do Software, como por exemplo, uma camada de dados, podemos dizer que é o complemento da classe
  Repository, nela decidimos o tipo de retorno dos métodos, a implementação dos métodos, tudo referente a conexão entre
  as entidades e o banco de dados.
  <br>

* [Controller](https://education.launchcode.org/java-web-development/chapters/spring-controllers-and-routes/controllers-simple.html)
  <br>O controller também é uma das classes que fazem parte das arquiteturas MVC, sendo responsável pelo processamento
  das requisições e das respostas. Podemos dizer que é a classe em que faz a conexão do usuário real e o programa
  desenvolvido por trás, realizando o controle das páginas.
  <br>

## 🔧 Questões técnicas

Vamos explorar as decisões técnicas que foram fundamentais para a modelagem do projeto:

A primeira e crucial decisão foi a escolha das ferramentas que utilizaremos. Optei por Java como linguagem de
programação devido à sua ampla adoção e suporte robusto na comunidade de desenvolvedores. Para construir a API de CRUD
necessária, optei pelo framework SpringBoot. Ele atua como uma "cola invisível" para o Java, unindo-se harmoniosamente a
componentes essenciais, como o Hibernate e o EntityManager. Essa combinação permitirá trabalhar de forma eficiente e
eficaz nos bastidores do projeto.

O SpringBoot oferece uma experiência de desenvolvimento aprimorada, através do uso de suas anotações intuitivas, teremos
uma base sólida para iniciar o projeto. Além disso, o Hibernate, uma ferramenta de mapeamento objeto-relacional,
simplifica a interação com o banco de dados, permitindo que concentremos nossos esforços no desenvolvimento de recursos
e na manipulação de objetos Java em vez de escrever consultas SQL detalhadas.

### Componentes da entidade

Por uma questão de padrão de arquitetura de Software, as entidades foram sub-dividas em 5 componentes:<br>
Model (```@Entity```), DTO, Repository (```@Repository```), Service (```@Service```) e Controller (```@Controller```),
aumentando
a [coesão do código](https://pt.stackoverflow.com/questions/81314/o-que-s%C3%A3o-os-conceitos-de-coes%C3%A3o-e-acoplamento)
se tornando mais simples para a correção de eventuais problemas.

#### Anotações importantes:

Possuem muitas anotações essenciais utilizadas, então por serem anotações "autoexplicativas", não as considerarei,
explicarei apenas aquelas que possuem uma objetificação mais específica.

```@EmbeddedId``` - Algumas entidades não possuem um único ID próprio, possuem ID's primárias, conhecidas
como [CompositeKey](https://www.baeldung.com/jpa-composite-primary-keys), pode ser implementada através de outros
métodos, como o @IdClass, entretanto, acredito que seja uma forma mais explícita de manipular e entender os ID's que
compõem a entidade. <br>

```@JsonIgnore``` - Alguns atributos, não necessito que sejam serializados, ou melhor dizendo, não quero que faça a
transfêrencia dos dados em específicos momentos do código, portanto, aviso ao Hibernate que este atributo específico não
necessita transferir os dados. <br>
<br>
Ex:

```
  @JsonIgnore 
    public EquipmentPositionHistoryPK getEquipmentPositionHistoryPK() {
        return equipmentPositionHistoryPK;
    }  
 ```   

Ao fazer isto, digo ao Hibernate para não se preocupar em retornar um objeto do
tipo EquipmentPositionHistory, no momento da geração do arquivo padrão [JSON](https://www.json.org/json-en.html)
<br>

```@Transactional``` - É utilizado para garantir que, antes de realizar uma transação de dados, é gerado um "backup" dos
dados persistidos, tratando todas ações referentes ao dado persistido seja uma transação única, em que, se algo der
errado durante essa transação de dados, por exemplo uma falha, a anotação @Transactional, realizará um rollback dos
dados, cooperando na integridade dos dados, geralmente está anotação é utilizada em ações do tipo salvar, editar,
deletar.
<br>
<br>
`@NotNull e @NotBlank` - Utilizado para designar que o valor do atributo não pode ser nulo/vazio.

#### Atributos das anotações das entidades

`fetch = FetchType.LAZY` - É o tipo de busca de dados padrão das anotações, a diferença entre fetchType.EAGER e
fetchType.LAZY, é que na anotação LAZY, ele apenas busca os dados da aplicação no momento que os dados forem utilizados,
evitando carregar todos os dados durante a iniciação da aplicação.
<br>
<br>

`mappedBy` - Informa por qual nome de atributo a entidade está sendo mapeada.
<br>

`cascade = CascadeType.ALL` - É o tipo das [propagações](https://www.baeldung.com/jpa-cascade-types) que uma ação na
entidade ocorrerá.
<br>

`orphanRemoval = true`  - Uma anotação que basicamente diz que, caso Equipment seja filho de EquipmentModel, se
EquipmentModel for excluído do banco de dados, seus filhos serão excluídos também.
<br>

Ex:

```
public class EquipmentModel {
@OneToMany(mappedBy = "equipmentModel", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Equipment> equipments;
} 
``` 

<br>

Caso EquipmentModel seja excluído, está exclusão se propagará para toda lista de Equipments referentes ao
EquipmentModel.

### Criação das páginas WEB

Por padrão do Spring MVC, as páginas HTML são armazenadas em `src/main/resources/templates`. Como o projeto não é focado
no frontend, são páginas simples.
<br>
Para organizar o mapeamento das nossas páginas, precisamos de uma classe `@Controller`, que está localizada
em `src/main/java/apirest/aiko/WebPages/MainController.java`. Esta classe é usada para mapear nossas páginas HTML e, se
necessário, passar dados do *backend* para o *frontend*, utilizando o *Thymeleaf* para a persistência destes dados.

Em nossa página "home", para criarmos botões que carregam outras páginas, necessitamos utilizar um script do JQuery:

```
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
               function carregarPagina(pagina) {
               $(".middle").load(pagina);
        }
    </script>
```

Neste script, carregamos uma nova página html mapeada em nosso `@Controller`, e a carregamos essa página em nossa
div `middle`.

### Implementação da API do Google Maps

Para compreender a API do Google Maps, sugiro a leitura
da [documentação](https://developers.google.com/maps/documentation/javascript/overview?hl=pt-br) disponível pelo Google.

Para realizar a implementação da API, que é dada em JavaScript, em nossa aplicação Java, podemos utilizar a
anotação `<script th:inline="javascript">` de nosso *Thymeleaf*,

### Dockerizando a aplicação

A conteinirização do aplicativo é algo bem simples de configurar, na pasta raíz do projeto, possuímos `Dockerfile`
e `docker-compose.yml`, que armazenam as configurações de nosso container.<br> Resumidamente, nesta aplicação criaremos
um container para armazenar o SpringBoot(container: jamilzin1APP), configurando o arquivo `Dockerfile` para executar
nosso arquivo .jar dentro do container.<br>
E outro container, para armazenar o banco de dados postgres(container: dbAPP), no arquivo `docker-compose.yml` sua
configuração é bem simples, apenas destaco a linha que definimos "volumes" que mapeamos a pasta "postgres-data" para
persistir os dados, preservando-os mesmo após as reiniciações do container, facilitando também para um possível backup.

## ⚙️ Como configurar para teste da aplicação

1. Clone this repo: ``` git clone https://github.com/jamilzin1/API_Rest_de_CRUD_SpringBoot.git ```
2. Abra o terminal no diretório do arquivo.
3. Execute o docker: ``` docker-compose up ```
4. Restaure o banco de dados da aplicação. Execute o terminal para a pasta onde está salvo o repositório e use
   ``` docker exec -i dbAPP pg_restore -U postgres -d jamilzin1_db_apirest_test < data.sql ```
5. Acesse http://localhost:8080/main
   <br>

https://github.com/jamilzin1/API_Rest_de_CRUD_SpringBoot/assets/105384228/d32b7a27-c459-424e-a122-35fc14305912

**Fim!**

Você está pronto para desfrutar da aplicação :)

## ▶️ Testes da aplicação

https://github.com/jamilzin1/API_Rest_de_CRUD_SpringBoot/assets/105384228/10868b88-cc1a-42e2-9d82-15cc73486b14

