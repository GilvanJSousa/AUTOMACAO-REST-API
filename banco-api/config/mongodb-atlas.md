# Guia de Configuracao MongoDB Atlas

## Passo a Passo para Configurar MongoDB Atlas

### 1. Acessar o MongoDB Atlas
- URL: [https://cloud.mongodb.com/v2/6634d1779da27930d01c3790#/overview](https://cloud.mongodb.com/v2/6634d1779da27930d01c3790#/overview)
- Faça login com suas credenciais

### 2. Obter String de Conexao

1. No dashboard do Atlas, clique no botao **"Connect"**
2. Escolha **"Connect your application"**
3. Selecione **"Node.js"** como driver
4. Copie a string de conexao fornecida

**Exemplo de string de conexao:**
```
mongodb+srv://usuario:senha@cluster0.xxxxx.mongodb.net/banco?retryWrites=true&w=majority
```

### 3. Configurar Network Access

1. No menu lateral, clique em **"Network Access"**
2. Clique em **"Add IP Address"**
3. Para desenvolvimento, adicione:
   - **Seu IP atual**: Clique em "Add Current IP Address"
   - **Acesso global**: Digite `0.0.0.0/0` (permite acesso de qualquer lugar)

### 4. Configurar Database Access

1. No menu lateral, clique em **"Database Access"**
2. Clique em **"Add New Database User"**
3. Configure:
   - **Username**: Escolha um nome de usuario
   - **Password**: Crie uma senha forte
   - **Database User Privileges**: Selecione "Read and write to any database"
4. Clique em **"Add User"**

### 5. Configurar Variaveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
# MongoDB Atlas
MONGO_URI=mongodb+srv://<seu_usuario>:<sua_senha>@<seu_cluster>.mongodb.net/banco?retryWrites=true&w=majority

# Outras configuracoes
JWT_SECRET=sua_chave_secreta_aqui
PORT=3000
GRAPHQLPORT=3001
```

**Substitua os valores:**
- `<seu_usuario>`: Usuario criado no passo 4
- `<sua_senha>`: Senha do usuario
- `<seu_cluster>`: Nome do seu cluster no Atlas

### 6. Testar Conexao

1. Instale as dependencias:
   ```bash
   npm install
   ```

2. Execute o script de seed:
   ```bash
   npm run seed
   ```

3. Se tudo estiver correto, voce vera:
   ```
   ✅ MongoDB Atlas conectado com sucesso
   Conectado ao MongoDB. Iniciando populacao de dados...
   4 contas criadas
   2 usuarios criados
   Dados populados com sucesso!
   ```

### 7. Iniciar as APIs

```bash
# Terminal 1 - API REST
npm run rest-api

# Terminal 2 - API GraphQL  
npm run graphql-api
```

## Vantagens do MongoDB Atlas

### 🔒 Seguranca
- **Criptografia**: Dados criptografados em repouso e transito
- **Autenticacao**: Autenticacao robusta com usuarios e senhas
- **Network Security**: Controle de acesso por IP
- **Audit Logs**: Logs detalhados de todas as operacoes

### 📊 Monitoramento
- **Metrics**: Performance e uso em tempo real
- **Alertas**: Notificacoes automaticas para problemas
- **Logs**: Logs detalhados para debugging
- **Charts**: Graficos de performance e uso

### 🔄 Backup e Recuperacao
- **Backup Automatico**: Backups diarios automaticos
- **Point-in-Time Recovery**: Recuperacao para qualquer momento
- **Retencao**: Dados mantidos por ate 7 anos
- **Restore**: Restauracao facil via interface web

### ⚡ Performance
- **Auto-scaling**: Escalabilidade automatica baseada em demanda
- **Global Clusters**: Distribuicao global para baixa latencia
- **Indexes**: Indexacao automatica para queries rapidas
- **Connection Pooling**: Pool de conexoes otimizado

### 🌐 Disponibilidade
- **99.95% Uptime**: SLA garantido de disponibilidade
- **Multi-region**: Replicacao em multiplas regioes
- **Failover**: Failover automatico em caso de problemas
- **Maintenance**: Manutencao programada com zero downtime

## Troubleshooting

### Erro de Conexao
```
❌ Erro ao conectar com MongoDB Atlas: Authentication failed
```
**Solucao**: Verifique usuario e senha no arquivo `.env`

### Erro de Network
```
❌ Erro ao conectar com MongoDB Atlas: Network timeout
```
**Solucao**: Adicione seu IP na whitelist do Atlas

### Erro de Permissoes
```
❌ Erro ao conectar com MongoDB Atlas: Access denied
```
**Solucao**: Verifique se o usuario tem permissoes de leitura/escrita

## Suporte

- **Documentacao**: [docs.mongodb.com/atlas](https://docs.mongodb.com/atlas/)
- **Comunidade**: [community.mongodb.com](https://community.mongodb.com/)
- **Suporte**: Disponivel 24/7 para planos pagos 