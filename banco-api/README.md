# Banco API - Documentação

## Visão Geral

O projeto é composto por duas APIs independentes que oferecem suporte a operações financeiras, com uma arquitetura baseada em REST e GraphQL.

- **API REST**: Roda na porta `3000` e gerencia endpoints de contas e transferências.
- **API GraphQL**: Roda na porta `3001` e fornece operações para consultas e mutações no domínio financeiro.

---

## Regras de Negócio

### Serviço de Contas

#### Regras para obter contas:

- A consulta de contas retorna uma lista paginada de contas, com um limite por página e uma página especificada.

#### Regras para obter conta por ID:

- A consulta de uma conta por ID deve retornar os detalhes da conta correspondente.

### Serviço de Autenticação de Usuário

#### Regras de autenticação:

- O nome de usuário e a senha são obrigatórios.

- O sistema valida se o nome de usuário e a senha fornecidos são corretos.

- Se as credenciais estiverem incorretas, o sistema retorna um erro informando que o usuário ou senha são inválidos.

#### Regras de geração de token:

- O token gerado deve ter um tempo de expiração de 1 hora.

#### Regras de verificação de token:

- O token de autenticação deve ser verificado para garantir que seja válido.

- Se o token for inválido ou expirado, o sistema retorna um erro de autenticação.

### Serviço de Transferências

#### Regras para realizar transferência:

- O valor mínimo para transferências é de R$10,00.

- Transferências acima de R$5.000,00 requerem um token de autenticação (token específico '123456').

- As contas de origem e destino devem estar ativas.

- A conta de origem deve ter saldo suficiente para realizar a transferência.

#### Regras para buscar transferências:

- As transferências podem ser consultadas de forma paginada, com limite de itens por página e página especificada.

- Deve ser possível consultar todas as transferências realizadas.

#### Regras para atualizar transferências:

- Transferências podem ser atualizadas, mas o valor de atualização não pode ser inferior a R$10,00.

- Deve ser validado se as contas de origem e destino existem e estão ativas.

- O saldo da conta de origem deve ser verificado antes de realizar a atualização.

- Transferências acima de R$5.000,00 também requerem autenticação.

#### Regras para modificar transferências:

- O valor de uma transferência pode ser modificado, mas o novo valor deve ser superior ou igual a R$10,00.

- As contas de origem e destino devem estar ativas e existir.

- O saldo das contas de origem e destino deve ser verificado antes da modificação.

#### Regras para remover transferências:

- A remoção de uma transferência deve reverter os saldos das contas de origem e destino.

- Caso a transferência não seja encontrada, o sistema retorna um erro.

- A conta de origem e a conta de destino devem existir e estar ativas.

#### Outras Regras Gerais

- Em todos os casos de falha (como saldo insuficiente ou contas inativas), o sistema deve retornar uma mensagem de erro detalhada e apropriada.

- As transferências são realizadas de forma síncrona e qualquer falha no processo de transferência gera um erro com uma mensagem explicativa.

## Pré-requisitos

Antes de iniciar, certifique-se de que você tenha as seguintes ferramentas instaladas:

- [Node.js](https://nodejs.org/)
- [MongoDB Atlas](https://cloud.mongodb.com/) (recomendado) ou [MongoDB](https://www.mongodb.com/) local
- Gerenciador de pacotes npm (vem com o Node.js)

---

## Instruções de Configuração

### 1. Configuração MongoDB Atlas (Recomendado)

#### Opção A: Usar MongoDB Atlas (Cloud)
1. **Acesse o MongoDB Atlas**: [https://cloud.mongodb.com/v2/6634d1779da27930d01c3790#/overview](https://cloud.mongodb.com/v2/6634d1779da27930d01c3790#/overview)

2. **Obter String de Conexão**:
   - No dashboard do Atlas, clique em "Connect"
   - Escolha "Connect your application"
   - Copie a string de conexão fornecida

3. **Configurar IP Whitelist**:
   - No Atlas, va em "Network Access"
   - Adicione seu IP atual ou `0.0.0.0/0` para acesso global

4. **Criar Usuário do Banco**:
   - No Atlas, va em "Database Access"
   - Crie um usuário com permissões de leitura/escrita

#### Opção B: MongoDB Local
1. **Instalar MongoDB**:
   - **Windows**: Baixe e instale o [MongoDB Community Server](https://www.mongodb.com/try/download/community)
   - **macOS**: `brew install mongodb-community`
   - **Linux**: `sudo apt-get install mongodb`

2. **Iniciar o MongoDB**:
   - **Windows**: O MongoDB deve iniciar automaticamente como serviço
   - **macOS/Linux**: `sudo systemctl start mongod` ou `brew services start mongodb-community`

### 2. Variáveis de Ambiente (`.env`)
Crie um arquivo `.env` na raiz do projeto com o seguinte conteúdo:

```env
# MongoDB Atlas (recomendado)
MONGO_URI=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/banco?retryWrites=true&w=majority

# MongoDB Local (alternativa)
# MONGO_URI=mongodb://localhost:27017/banco

JWT_SECRET=sua_chave_secreta
PORT=3000
GRAPHQLPORT=3001
```

**Substitua os valores**:
- `<username>`: Seu usuário do MongoDB Atlas
- `<password>`: Sua senha do MongoDB Atlas  
- `<cluster>`: Nome do seu cluster no Atlas

---

## Instruções de Execução

### 1. Instalar Dependências
Execute o comando abaixo na raiz do projeto:
```bash
npm install
```

### 2. Popular Dados Iniciais
```bash
npm run seed
```

### 3. Executar APIs
- Para iniciar a **API REST**:
  ```bash
  npm run rest-api
  ```
- Para iniciar a **API GraphQL**:
  ```bash
  npm run graphql-api
  ```

---

## Serviços Disponíveis Após a Inicialização

### ApolloServer
Depois de iniciar a API GraphQL, o ApolloServer estará disponível na porta `3001`. Ele oferece uma interface interativa no endereço [http://localhost:3001/graphql](http://localhost:3001/graphql), onde é possível explorar o schema, testar queries e mutações, e visualizar resultados em tempo real.

O ApolloServer é uma biblioteca amplamente utilizada para implementar servidores GraphQL. Ele simplifica o desenvolvimento, fornecendo suporte para definir schemas, resolvers, autenticação, entre outros recursos.

### Swagger
Após a inicialização da API REST, a documentação interativa estará disponível no Swagger, no endereço [http://localhost:3000/api-docs](http://localhost:3000/api-docs). Essa documentação permite:
- Explorar os endpoints disponíveis.
- Testar requisições diretamente pelo navegador.
- Obter informações detalhadas sobre parâmetros, respostas e erros.

Para visualizar o Swagger, certifique-se de que a API REST esteja em execução.

---

## Estrutura do Projeto

```plaintext
project/
├── src/
│   ├── models/
│   │   ├── db.js (configuração MongoDB Atlas)
│   │   ├── contasModel.js (schema Mongoose)
│   │   ├── usuariosModel.js (schema Mongoose)
│   │   └── transferenciasModel.js (schema Mongoose)
│   └── services/
│       ├── contaService.js
│       ├── loginService.js
│       └── transferenciaService.js
├── rest/
│   ├── app.js
│   ├── controllers/
│   │   ├── contaController.js
│   │   ├── loginController.js
│   │   └── transferenciaController.js
│   ├── middlewares/
│   │   └── authMiddleware.js
│   ├── routes/
│   │   ├── contaRoutes.js
│   │   ├── loginRoutes.js
│   │   └── transferenciaRoutes.js
├── graphql/
│   ├── app.js
│   ├── resolvers/
│   │   ├── index.js
│   │   ├── queryResolvers.js
│   │   └── mutationResolvers.js
│   ├── schema/
│   │   └── index.js
│   ├── typeDefs.js
├── scripts/
│   └── seed.js (população de dados iniciais)
├── config/
│   └── serverConfig.js
├── .env
├── package.json
└── README.md
```

---

## GraphQL API

### Endpoints
- URL da API GraphQL: `http://localhost:3001/graphql`

### Definições do Schema (`typeDefs`)
- **Queries**:
  - `contas`: Retorna a lista de contas.
  - `transferencias(page: Int, limit: Int)`: Retorna uma lista paginada de transferências.
- **Mutations**:
  - `login(username: String!, senha: String!)`: Autentica um usuário e retorna um token JWT.
  - `transferir(contaOrigem: Int!, contaDestino: Int!, valor: Float!, mfaToken: String!)`: Realiza uma transferência.

### Exemplo de Requisição com `curl`

#### Consulta de Contas
```bash
curl -X POST http://localhost:3001/graphql -H "Content-Type: application/json" -d '{"query": "{ contas { id titular saldo ativa } }"}'
```

#### Transferência de Fundos
```bash
curl -X POST http://localhost:3001/graphql -H "Content-Type: application/json" -d '{"query": "mutation { transferir(contaOrigem: 1, contaDestino: 2, valor: 100.0, mfaToken: \"123456\") }"}'
```

---

## REST API

### Endpoints
Base URL: `http://localhost:3000`

- **GET /contas**: Retorna todas as contas.
- **POST /login**: Realiza a autenticação de um usuário.
- **POST /transferencias**: Realiza uma transferência entre contas.

---

## Migracao MySQL para MongoDB Atlas

Este projeto foi migrado de MySQL para MongoDB Atlas. As principais mudanças incluem:

### Mudanças Técnicas
- **Banco de dados**: MySQL → MongoDB Atlas (Cloud)
- **ORM**: Queries SQL → Mongoose ODM
- **IDs**: Auto-increment → ObjectId
- **Relacionamentos**: Foreign Keys → Referencias
- **Infraestrutura**: Local → Cloud (MongoDB Atlas)

### Beneficios da Migracao
- **Flexibilidade**: Schema flexível para evolução rápida
- **Performance**: Melhor performance para operações de leitura
- **Escalabilidade**: Horizontal scaling nativo
- **Desenvolvimento**: Documentos JSON nativos
- **Confiabilidade**: Backup automático e alta disponibilidade
- **Acesso Remoto**: Conexão de qualquer lugar
- **Monitoramento**: Dashboard integrado do Atlas

### Consideracoes Importantes
- **Transacoes**: MongoDB suporta transações, mas com limitações
- **Integridade**: Validacoes devem ser implementadas no codigo
- **Relacionamentos**: Usar `populate()` para joins
- **Indexacao**: Configurar índices para performance ótima
- **Conexao**: Configurar IP whitelist no Atlas
- **Seguranca**: Usar variaveis de ambiente para credenciais

### Vantagens do MongoDB Atlas
- **Backup Automático**: Backups diários automáticos
- **Monitoramento**: Metrics e alertas integrados
- **Seguranca**: Criptografia em repouso e transito
- **Escalabilidade**: Auto-scaling baseado em demanda
- **Disponibilidade**: 99.95% uptime garantido
- **Suporte**: Suporte 24/7 disponível

## 📊 Consultando dados diretamente no MongoDB Atlas

Você pode consultar todos os dados do banco (usuários, contas, transferências) a qualquer momento usando o comando:

```sh
npm run consultar
```

### Exemplo de uso

```sh
npm run consultar
```

### Saída esperada

```
🔍 Verificando configuração:
   MONGO_URI definida: ✅ Sim
   Usando URI: ✅ Atlas
✅ MongoDB Atlas conectado com sucesso

🔄 Consultando dados no MongoDB Atlas...

👤 USUARIOS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
  1. admin
     ID: 6866d50e8b9b1ff293d31296
  2. usuario
     ID: 6866d50e8b9b1ff293d31297

💰 CONTAS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
  1. Ana Costa
     Saldo: R$ 3000.00
     Status: ❌ Inativa
     ID: 6866d50d8b9b1ff293d31291
  2. Joao Silva
     Saldo: R$ 1000.00
     Status: ✅ Ativa
     ID: 6866d50d8b9b1ff293d3128e
  ...

💸 TRANSFERENCIAS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
  1. Transferência 6866beb554f7e3896d3a9f39
     De: Joao Silva
     Para: Maria Santos
     Valor: R$ 150.00
     Autenticada: ❌ Não
     Data: 03/07/2025 15:00:00
  ...

📊 RESUMO:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   👤 Usuários: 2
   💰 Contas: 4
   💸 Transferências: 3

🚀 Dados consultados com sucesso!
💡 Use os IDs das contas para testar transferências
🔌 Desconectado do MongoDB Atlas
```

Sempre que quiser conferir os dados reais do banco, basta rodar esse comando!