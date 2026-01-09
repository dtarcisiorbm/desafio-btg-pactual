# Desafio BTG Pactual - Microsserviço de Pedidos

Este projeto é uma solução para o desafio técnico do BTG Pactual. Trata-se de um microsserviço desenvolvido em **Java** com **Spring Boot** que processa pedidos recebidos via fila de mensagens (**RabbitMQ**), armazena-os em um banco de dados NoSQL (**MongoDB**) e disponibiliza uma API REST para consulta do valor total e quantidade de pedidos por cliente.

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.x**
* **MongoDB** (Persistência de dados)
* **RabbitMQ** (Mensageria e processamento assíncrono)
* **Docker & Docker Compose** (Containerização da infraestrutura)
* **Maven** (Gerenciamento de dependências)

## 🏗️ Arquitetura

1.  **Consumo:** A aplicação escuta a fila `btg-pactual-order-created` no RabbitMQ.
2.  **Processamento:** Ao receber uma mensagem (evento de pedido criado), o sistema calcula o valor total do pedido.
3.  **Persistência:** Os dados consolidados são salvos na coleção `tb_orders` no MongoDB.
4.  **API:** Um endpoint REST é exposto para consultar o histórico de pedidos de um cliente específico.

## ⚙️ Pré-requisitos

* [Docker](https://www.docker.com/) e Docker Compose instalados.
* Java 21 (JDK) instalado (opcional se usar apenas para build, o projeto possui Maven Wrapper).

## 👣 Passo a Passo para Rodar

### 1. Clonar o repositório

```bash
git clone [https://github.com/seu-usuario/desafio-btg-pactual.git](https://github.com/seu-usuario/desafio-btg-pactual.git)
cd desafio-btg-pactual
