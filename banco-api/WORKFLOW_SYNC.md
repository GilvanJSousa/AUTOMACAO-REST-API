# 🔄 Sincronização Automática de Portas - Workflow GitHub Actions

Este documento explica como funciona a sincronização automática entre as portas configuradas no projeto e o arquivo de workflow do GitHub Actions.

## 📋 Visão Geral

Quando você altera a porta da API REST através do sistema de configuração, a porta é automaticamente sincronizada com o arquivo `.github/workflows/testes-11-e-21.yml` para garantir que os testes de CI rodem na porta correta.

## 🔧 Como Funciona

### 1. **Sincronização Automática**
- Sempre que você altera a porta via `config-manager.js`, a sincronização acontece automaticamente
- O script lê a porta atual do arquivo `.env` e atualiza o workflow
- Não é necessário fazer nada manualmente

### 2. **Fonte da Verdade**
- **Arquivo `.env`**: Contém a porta atual em uso
- **Workflow**: É atualizado automaticamente para usar a mesma porta

## 🚀 Comandos Disponíveis

### Sincronização Automática (Recomendado)
```bash
# Alterar porta REST (sincronização automática)
npm run config:rest-port 9090

# Alterar ambas as portas (sincronização automática)
npm run config:update-ports 9090 9091
```

### Sincronização Manual
```bash
# Sincronizar manualmente
npm run config:sync-workflow

# Ou usar o script direto
npm run workflow:sync

# Ver informações de sincronização
npm run workflow:info
```

### Comandos Diretos
```bash
# Via config-manager
node scripts/config-manager.js sync-workflow

# Via script de sincronização
node scripts/sync-workflow-port.js sync
node scripts/sync-workflow-port.js info
```

## 📁 Arquivos Envolvidos

### Arquivos de Configuração
- `config/properties.json` - Configuração principal
- `.env` - Variáveis de ambiente (gerado automaticamente)

### Scripts
- `scripts/config-manager.js` - Gerenciador de configurações
- `scripts/sync-workflow-port.js` - Sincronizador de workflow

### Workflow
- `.github/workflows/testes-11-e-21.yml` - Workflow do GitHub Actions

## 🔄 Fluxo de Sincronização

```
1. Usuário altera porta via config-manager.js
   ↓
2. properties.json é atualizado
   ↓
3. .env é gerado/atualizado
   ↓
4. sync-workflow-port.js é chamado automaticamente
   ↓
5. Workflow YAML é atualizado com a nova porta
```

## ✅ Exemplo de Uso

### Cenário: Alterar porta de 3000 para 9090

```bash
# 1. Alterar a porta
npm run config:rest-port 9090

# Saída esperada:
# ✅ Configurações salvas com sucesso!
# 🔄 Porta REST alterada para: 9090
# ✅ Arquivo .env atualizado com sucesso!
# ✅ Workflow atualizado com sucesso!
# 🔄 Porta alterada para: 9090
```

### Verificar se funcionou

```bash
# Verificar configuração atual
npm run config:show

# Verificar sincronização
npm run workflow:info
```

## 🛠️ Troubleshooting

### Erro: "Arquivo .env não encontrado"
```bash
# Solução: Criar arquivo .env primeiro
npm run create-env
```

### Erro: "Arquivo de workflow não encontrado"
- Verifique se o arquivo `.github/workflows/testes-11-e-21.yml` existe
- Certifique-se de que está executando o comando na raiz do projeto

### Sincronização não funcionou
```bash
# Forçar sincronização manual
npm run config:sync-workflow
```

## 🔍 Verificação Manual

Para verificar se a sincronização funcionou:

1. **Verificar arquivo .env**:
   ```bash
   cat .env | grep PORT
   ```

2. **Verificar workflow**:
   ```bash
   cat .github/workflows/testes-11-e-21.yml | grep "PORT:"
   ```

3. **Usar comando de info**:
   ```bash
   npm run workflow:info
   ```

## 📝 Notas Importantes

- ✅ A sincronização é **automática** quando você usa `config-manager.js`
- ✅ O workflow é atualizado **imediatamente** após alterar a porta
- ✅ Não é necessário fazer commit manual do workflow
- ⚠️ Se o arquivo de workflow não existir, a sincronização é ignorada (normal em desenvolvimento)
- 🔄 A sincronização é **idempotente** - pode ser executada múltiplas vezes sem problemas

## 🎯 Benefícios

1. **Consistência**: Garante que CI e desenvolvimento usem a mesma porta
2. **Automatização**: Não precisa lembrar de atualizar o workflow manualmente
3. **Confiabilidade**: Reduz erros de configuração entre ambientes
4. **Rastreabilidade**: Facilita debugging de problemas de CI 