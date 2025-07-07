# Sistema de Configuração de Portas

Este documento explica como usar o sistema de configuração de portas do projeto Banco API.

## 📁 Arquivos de Configuração

### 1. `config/properties.json`
Arquivo principal de configuração que contém todas as configurações do projeto:

```json
{
  "server": {
    "rest": {
      "port": 8080,
      "host": "localhost"
    },
    "graphql": {
      "port": 8081,
      "host": "localhost"
    }
  },
  "database": {
    "mongo_uri": "mongodb+srv://..."
  },
  "jwt": {
    "secret": "banco_api_jwt_secret_key_2024"
  },
  "swagger": {
    "enabled": true,
    "path": "/api-docs"
  }
}
```

### 2. `.env`
Arquivo gerado automaticamente baseado no `properties.json`:

```env
MONGO_URI=mongodb+srv://...
JWT_SECRET=banco_api_jwt_secret_key_2024
PORT=8080
GRAPHQLPORT=8081
```

## 🛠️ Como Alterar as Portas

### Método 1: Usando o Gerenciador de Configurações (Recomendado)

#### Verificar configurações atuais:
```bash
npm run config:show
```

#### Listar portas sugeridas:
```bash
npm run config:list-ports
```

#### Alterar porta do servidor REST:
```bash
node scripts/config-manager.js rest-port 9090
```

#### Alterar porta do servidor GraphQL:
```bash
node scripts/config-manager.js graphql-port 9091
```

#### Alterar ambas as portas de uma vez:
```bash
node scripts/config-manager.js update-ports 9090 9091
```

### Método 2: Editando o Arquivo Properties Manualmente

1. Abra o arquivo `config/properties.json`
2. Altere os valores das portas:
   ```json
   "server": {
     "rest": {
       "port": 9090,  // Nova porta REST
       "host": "localhost"
     },
     "graphql": {
       "port": 9091,  // Nova porta GraphQL
       "host": "localhost"
     }
   }
   ```
3. Execute o comando para atualizar o `.env`:
   ```bash
   node scripts/config-manager.js show
   ```

### Método 3: Usando Variáveis de Ambiente

```bash
# Windows PowerShell
$env:PORT=9090
$env:GRAPHQLPORT=9091
npm run rest-api

# Windows CMD
set PORT=9090
set GRAPHQLPORT=9091
npm run rest-api

# Linux/Mac
PORT=9090 GRAPHQLPORT=9091 npm run rest-api
```

## 🚀 Portas Sugeridas

| Porta | Descrição |
|-------|-----------|
| 8080/8081 | Portas padrão para desenvolvimento web |
| 4000/4001 | Portas alternativas populares |
| 5000/5001 | Portas usadas anteriormente no projeto |
| 8000/8001 | Portas de desenvolvimento |
| 9000/9001 | Portas de alta numeração |

## 📋 Comandos Disponíveis

### Scripts NPM
```bash
npm run config              # Mostrar ajuda do gerenciador
npm run config:show         # Exibir configurações atuais
npm run config:list-ports   # Listar portas sugeridas
```

### Comandos Diretos
```bash
node scripts/config-manager.js show                    # Exibir configurações
node scripts/config-manager.js rest-port 9090         # Alterar porta REST
node scripts/config-manager.js graphql-port 9091      # Alterar porta GraphQL
node scripts/config-manager.js update-ports 9090 9091 # Alterar ambas as portas
node scripts/config-manager.js list-ports             # Listar portas sugeridas
```

## 🔄 Fluxo de Atualização

1. **Alteração no `properties.json`** → Atualiza configurações
2. **Geração automática do `.env`** → Aplica configurações
3. **Reinicialização do servidor** → Carrega novas configurações

## ⚠️ Importante

- Sempre reinicie o servidor após alterar as portas
- Verifique se a porta escolhida não está sendo usada por outro processo
- O arquivo `.env` é gerado automaticamente, não edite manualmente
- Todas as alterações devem ser feitas no `config/properties.json`

## 🧪 Testando as Configurações

Após alterar as portas, teste se os servidores estão funcionando:

```bash
# Iniciar servidor REST
npm run rest-api

# Em outro terminal, iniciar servidor GraphQL
npm run graphql-api

# Verificar se estão rodando
curl http://localhost:8080/api-docs  # REST
curl http://localhost:8081           # GraphQL
```

## 🔧 Troubleshooting

### Porta já em uso
```bash
# Windows - Verificar portas em uso
netstat -an | findstr :8080

# Linux/Mac - Verificar portas em uso
lsof -i :8080
```

### Configurações não aplicadas
1. Verifique se o arquivo `.env` foi atualizado
2. Reinicie o servidor
3. Verifique se não há variáveis de ambiente sobrescrevendo as configurações

### Erro de conexão
1. Verifique se o MongoDB está acessível
2. Confirme se as credenciais estão corretas
3. Teste a conectividade de rede 