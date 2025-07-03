# ☕ Seu Cafézinho

## 📄 Descrição
Sistema para gerenciamento de um café, com integração a dois microsserviços responsáveis pela comunicação com o cliente:

**email-service**: envia e-mails de boas-vindas via SMTP (Gmail)

**sms-service**: envia SMS com atualizações de pedido via AWS SNS (Simple Notification Service)

---

## ⚙️ Funcionalidades
- **Gerenciamento do café**: criação de cardápio e produtos pelos administradores.
- **Gerenciamento de pedidos**: atualização de status dos pedidos pelos administradores.
- **Envio de e-mail de boas-vindas**: para novos usuários, via **email-service**.
- **Envio de SMS com atualizações de pedido**: para usuários, via **sms-service**.

---

## 🛠️ Tecnologias
- **Linguagem**: Java
- **Framework**: Spring Boot
- **Gerenciador de Dependências**: Maven
- **Banco de Dados**: PostgreSQL
- **Migrations**: Flyway
- **Conversão de Objetos**: Mapstruct
- **Anotações**: Lombok
- **Autenticação**: JWT (JSON Web Token)
- **Testes Unitários**: JUnit e Mockito
- **Mensageria**: RabbitMQ
- **Broker na Nuvem**: Cloud AMQP
- **Serviço de SMS**: Amazon SNS
- **Serviço de E-mail**: SMTP Gmail
- **Documentação**: SwaggerUI

---

## 📝 Endpoints
- Documentação **local**: http://localhost:8080/swagger-ui/index.html

---

## 📈 Diagramas
<details>
    <summary><b>Diagrama de Entidade-Relacionamento</b></summary>
    <img src="assets/diagrama-er.png" alt="Diagrama de Entidade-Relacionamento" width=700>
</details>

<details>
    <summary><b>Diagrama de Mensageria</b></summary>
    <img src="assets/diagrama-rabbitmq.png" alt="Diagrama de Mensageria" width=600>
</details>

---

## 🗂️ Imagens do Projeto
<details>
    <summary><b>Envio de Email</b></summary>
    <img src="assets/email-exemplo.png" alt="Exemplo do Email", width=650>
</details>

<details>
    <summary><b>Envio de SMS</b></summary>
    <img src="assets/sms-exemplo.jpg" alt="Exemplo de SMS" height=400>
</details>

---

## ➡️ Fluxo da Aplicação
1. O usuário é criado.
2. Um e-mail de boas-vindas é enviado (**email-service**).
3. Um pedido é criado pelo usuário.
4. O status do pedido é atualizado pelo administrador.
5. Um SMS é enviado ao usuário com a atualização do pedido (**sms-service**).

---

## ⚙️ Configuração e Execução

**Pré-requisitos**:
- Java 17
- Maven
- PostgreSQL
- RabbitMQ (CloudAMQP)
- Conta na AWS (Amazon SNS)
- Credenciais de servidor de e-mail (SMTP Gmail)

**Passos para Configuração**:
1. Clone o repositório
2. Acesse o diretório do projeto
3. Configure o banco de dados no arquivo `application.yml` (URL, usuário, senha)
4. Configure o RabbitMQ (CloudAMQP) no arquivo `application.yml`
5. Configure a AWS SNS com suas credenciais no arquivo `application.yml`
6. Configure as credenciais do servidor de e-mail (SMTP Gmail) no arquivo `application.yml` (host, porta, usuário e senha)

```bash
# Execute a aplicação
mvn spring-boot:run

# Pressione (CTRL + C) para encerrar a aplicação
```

---

## 🙋‍♀️ Autor
👩‍💻 Projeto desenvolvido por [Isabel Henrique](https://www.linkedin.com/in/isabel-henrique/)

🤝 Fique à vontade para contribuir!
