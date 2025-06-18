# Automação de Testes API REST - Serverest

Este projeto contém testes automatizados para a API Serverest, uma API REST para simulação de um e-commerce. A API está disponível em http://localhost:3000/ e oferece endpoints para gerenciamento de usuários, produtos e carrinhos de compras.

## 🚀 Tecnologias Utilizadas

- Java 8+
- Cucumber
- JUnit
- RestAssured
- Lombok
- Maven

## 📋 Pré-requisitos

- Java JDK 8 ou superior
- Maven
- Node.js (para rodar o Serverest localmente)

## 🔧 Instalação

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/automacao-rest-api.git
cd automacao-rest-api
```

2. Instale as dependências do Maven:
```bash
mvn clean install
```

3. Instale e inicie o Serverest:
```bash
npm install -g serverest@latest
serverest
```

## 🏃‍♂️ Executando os Testes

### Executar todos os testes
```bash
mvn test
```

### Executar testes específicos por tag
```bash
mvn test -Dcucumber.filter.tags="@FuncionalideUsuario"
mvn test -Dcucumber.filter.tags="@FuncionalideProdutos"
mvn test -Dcucumber.filter.tags="@FuncionalidadesCarrinhos"
```

## 📝 Estrutura do Projeto

```
src/test/
├── java/org/br/com/testes/
│   ├── controllers/         # Controladores para cada entidade
│   │   ├── usuarios/       # Controlador de usuários
│   │   ├── produtos/       # Controlador de produtos
│   │   └── carrinhos/      # Controlador de carrinhos
│   ├── manager/            # Gerenciadores de estado
│   │   ├── TokenManager    # Gerenciamento de tokens
│   │   ├── UsuarioManager  # Gerenciamento de usuários
│   │   ├── ProdutosManager # Gerenciamento de produtos
│   │   └── CarrinhosManager # Gerenciamento de carrinhos
│   ├── model/              # Modelos de dados
│   │   ├── usuario/        # Modelos de usuário
│   │   ├── produtos/       # Modelos de produto
│   │   └── carrinhos/      # Modelos de carrinho
│   ├── steps/              # Implementações dos steps do Cucumber
│   └── utils/              # Utilitários (FakerApiData, etc)
└── resources/
    └── features/           # Arquivos .feature do Cucumber
        ├── usuarios.feature
        ├── produtos.feature
        └── carrinhos.feature
```

## 🔍 Funcionalidades Testadas

### Usuários (@FuncionalideUsuario)
- Cadastro de usuário (CT-1001)
- Login (CT-1002)
- Busca por ID (CT-1003)
- Listagem (CT-1004)
- Edição (CT-1005)
- Exclusão (CT-1006)
- Validação de email duplicado (CT-1007)

### Produtos (@FuncionalideProdutos)
- Cadastro de produto (CT-2001)
- Busca por ID (CT-2002)
- Listagem (CT-2003)
- Edição (CT-2004)
- Exclusão (CT-2005)

### Carrinhos (@FuncionalidadesCarrinhos)
- Cadastro de carrinho (CT-3001)
- Busca por ID (CT-3002)
- Listagem (CT-3003)
- Exclusão (CT-3004)
- Cancelamento de compra e reabastecimento de estoque (CT-3005)

## 📊 Relatórios

Os relatórios dos testes são gerados em:
- HTML: `target/cucumber-reports/cucumber.html`
- JSON: `target/cucumber-reports/cucumber.json`

## 🔗 Documentação da API

A API Serverest está disponível em:
- Local: http://localhost:3000
- Online: https://serverest.dev

### Endpoints Principais

#### Usuários
- POST /usuarios - Cadastrar usuário
- POST /login - Login
- GET /usuarios - Listar usuários
- GET /usuarios/{id} - Buscar usuário
- PUT /usuarios/{id} - Editar usuário
- DELETE /usuarios/{id} - Excluir usuário

#### Produtos
- POST /produtos - Cadastrar produto
- GET /produtos - Listar produtos
- GET /produtos/{id} - Buscar produto
- PUT /produtos/{id} - Editar produto
- DELETE /produtos/{id} - Excluir produto

#### Carrinhos
- POST /carrinhos - Cadastrar carrinho
- GET /carrinhos - Listar carrinhos
- GET /carrinhos/{id} - Buscar carrinho
- PUT /carrinhos/{id} - Editar carrinho
- DELETE /carrinhos/concluir-compra - Concluir compra
- DELETE /carrinhos/cancelar-compra - Cancelar compra

## 🔐 Autenticação

A API utiliza autenticação via token Bearer. O token é obtido através do endpoint de login e deve ser incluído no header `Authorization` de todas as requisições que necessitam de autenticação.

Exemplo de uso do token:
```java
given()
    .header("Authorization", "Bearer " + token)
    .contentType(ContentType.JSON)
    .when()
    .get("/endpoint")
```

## 📦 Dados de Teste

O projeto utiliza a biblioteca Faker para gerar dados aleatórios para os testes:
- Usuários: nome, email, senha
- Produtos: nome, preço, descrição, quantidade
- Carrinhos: produtos e quantidades
