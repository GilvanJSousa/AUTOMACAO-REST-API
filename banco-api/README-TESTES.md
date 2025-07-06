# 🧪 Testes da API Banco - Ordem de Execução

## 📋 Lista de Arquivos .bat Organizados

### 🔐 **01-login.bat**
- **Função**: Obter token JWT para autenticação
- **Endpoint**: `POST /login`
- **Executar primeiro** para obter token válido

### 💰 **02-transferencia.bat**
- **Função**: Realizar transferência de R$ 50,00
- **Endpoint**: `POST /transferencia`
- **Dependência**: Token JWT do login

### 📊 **03-listar-transferencias.bat**
- **Função**: Listar todas as transferências
- **Endpoint**: `GET /transferencia`
- **Dependência**: Token JWT do login

### 🔍 **04-consultar-transferencia.bat**
- **Função**: Consultar transferência específica por ID
- **Endpoint**: `GET /transferencia/{id}`
- **Dependência**: Token JWT do login

### 📋 **05-get-transferencias.bat**
- **Função**: Listar transferências com paginação
- **Endpoint**: `GET /transferencia?page=1&limit=10`
- **Dependência**: Token JWT do login

### 🔎 **06-get-transferencia-id.bat**
- **Função**: Obter transferência por ID específico
- **Endpoint**: `GET /transferencia/{id}`
- **Dependência**: Token JWT do login

### ✏️ **07-patch-transferencia.bat**
- **Função**: Atualizar parcialmente uma transferência
- **Endpoint**: `PATCH /transferencia/{id}`
- **Dependência**: Token JWT do login

### 🗑️ **08-delete-transferencia.bat**
- **Função**: Remover uma transferência
- **Endpoint**: `DELETE /transferencia/{id}`
- **Dependência**: Token JWT do login

### 🏦 **09-get-contas.bat**
- **Função**: Listar todas as contas bancárias
- **Endpoint**: `GET /contas`
- **Dependência**: Token JWT do login

### 👤 **10-get-conta-id.bat**
- **Função**: Obter detalhes de uma conta específica
- **Endpoint**: `GET /contas/{id}`
- **Dependência**: Token JWT do login

## 🚀 Como Executar

1. **Sempre execute primeiro**: `01-login.bat`
2. **Execute os demais na ordem numérica** conforme necessário
3. **Todos os testes funcionam** com MongoDB Atlas

## 📝 Observações

- ✅ Todos os arquivos foram testados e funcionam corretamente
- ✅ Removidos arquivos duplicados e que não funcionavam
- ✅ Numerados em ordem lógica de execução
- ✅ Usam ObjectIds válidos do MongoDB
- ✅ Incluem autenticação JWT correta

## 🔧 Arquivos de Suporte

- `curls-postman.txt` - CURLs para Postman
- `curl-atualizar-transferencia.txt` - CURL específico para atualização 