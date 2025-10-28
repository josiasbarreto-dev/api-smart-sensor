# 🚀 API Smart Sensor

## 🌟 Visão Geral
API Smart Sensor é uma API RESTful em Java com Spring Boot para ingestão e gestão de leituras de sensores IoT. Oferece endpoints para receber dados de sensores, consultar leituras no MongoDB, deletar leituras por id e listar todas as leituras. Preparada para validação básica, persistência em MongoDB e execução em Docker.

## 📄 Descrição da API
A API Smart Sensor é a solução robusta e escalável para transformar dados brutos de sensores em insights acionáveis. Nosso objetivo é fornecer uma fundação sólida e de alta performance para projetos de monitoramento e análise de dados IoT.
## ⚙️ Tecnologias Utilizadas
| Ícone | Componente | Tecnologia                                                              |
| :---: | :--- |:------------------------------------------------------------------------|
| ☕ | **Linguagem** | Java 17 (LTS)                                                           |
| 🍃 | **Framework** | Spring Boot 3.x                                                         |
| 🗄️ | **Persistência** | MongoDB                                                                 |
| 📚 | **Documentação da API** | Swagger (OpenAPI)                                                       |
| 🐳 | **Containerização** | Docker                                                                  |
| 📦 | **Gerenciador de Dependências** | Maven                                                                   |
| 🗺️ | **Mapeamento de Objetos** | MapStruct                                                               |
| 🌐 | **API Web** | RESTful                                                                 |
| ✅ | **Testes** | JUnit 5, TestContainers e Mockito                                       |
| 🔗 | **Repositório** | [GitHub](https://github.com/josiasbarreto-dev/api-voting-challenge.git) |

## ✅ Funcionalidades Principais da API
### 🔑 CRUD Operations
| Método HTTP | Endpoint                                   | Descrição                                                                      |
|:------------|:-------------------------------------------|:-------------------------------------------------------------------------------|
| `POST`      | `/api/v1/sensors`                          | Cadastra dados recebidos do sensor.                                            |
| `GET`       | `/api/v1/sensors/{id}`                     | Busca dados de um sensor cadastrado por ID.                                    |
| `DELETE`    | `/api/v1/sensors/{id}`                       | Exclui dados de um sensor cadastrado por ID.                                   |
| `GET`       | `/api/v1/sensors?page=0&size=10` | Retorna uma lista paginada(page e size) com os dados dos sensores cadastrados. |

## 🚀 Como Executar o Projeto

### Pré-requisitos:

Certifique-se de que os seguintes softwares estão instalados em sua máquina:

* **[Git](https://git-scm.com/downloads)** (para clonar o repositório)
* **[Docker](https://www.docker.com/get-started/)**

### Passo a Passo:

1.  **Clone o Repositório:**
    Abra o terminal e execute o comando abaixo para baixar o código-fonte do projeto.
    ```bash
    git clone https://github.com/josiasbarreto-dev/api-smart-sensor.git
    ```

2.  **Baixe a imagem do MongoDB no DockerHub:**
    ```bash
    docker pull mongo:7.0
    ```

3.  **Execute o docker run para subir um container do Mongo**: Na raiz do projeto, execute o comando abaixo.
    ```bash
    docker run -d -p 27017:27017 --name mongodb mongo:7.0
    ```
4. **Execute a Aplicação Spring:**
    Navegue até o diretório do projeto clonado e execute o comando abaixo para iniciar a aplicação Spring Boot.
    ```bash
    ./mvnw spring-boot:run
    ```
Sua aplicação estará disponível em `http://localhost:8080`.

## ✍️ Autor

Este projeto foi desenvolvido por:

* **Josias Barreto** - [GitHub](https://github.com/josiasbarreto-dev)
* **LinkedIn** - [Josias Barreto](https://www.linkedin.com/in/josiasbarreto-dev/)