# Configuração do GitHub Actions

## 🔐 Configuração de Secrets

### **⚠️ ATENÇÃO**: Configuração Temporária para Teste

**Para fins de teste**, as credenciais estão hardcoded no workflow. **IMPORTANTE**: Após os testes, você DEVE configurar os secrets no GitHub por segurança.

### **Configuração Atual (Temporária)**
```yaml
env:
  MONGO_URI: mongodb+srv://gillvanjs:KNAqSlJj0n6mawLV@cluster0.yia8ilv.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
  JWT_SECRET: KNAqSlJj0n6mawLV
```

### **Configuração Segura (Após Testes)**

Para configurar os secrets no GitHub (RECOMENDADO):

1. **Acesse seu repositório no GitHub**
2. **Vá em Settings > Secrets and variables > Actions**
3. **Clique em "New repository secret"**
4. **Adicione os seguintes secrets:**

#### **MONGO_URI**
```
mongodb+srv://gillvanjs:KNAqSlJj0n6mawLV@cluster0.yia8ilv.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
```

#### **JWT_SECRET**
```
KNAqSlJj0n6mawLV
```

**Depois de configurar os secrets, altere o workflow para:**
```yaml
env:
  MONGO_URI: ${{ secrets.MONGO_URI }}
  JWT_SECRET: ${{ secrets.JWT_SECRET }}
```

## 🚀 Melhorias Implementadas no Workflow

### **1. Segurança**
- ✅ **Credenciais movidas para secrets** - Não mais expostas no código
- ✅ **Uso de versões mais recentes** das actions
- ✅ **Cache otimizado** para dependências Maven

### **2. Performance**
- ✅ **Cache de dependências Maven** - Acelera builds subsequentes
- ✅ **Verificação inteligente do servidor** - Não mais sleep fixo
- ✅ **Uso de `npm ci`** - Instalação mais rápida e determinística

### **3. Confiabilidade**
- ✅ **Timeout configurado** para inicialização do servidor
- ✅ **Logs mais informativos** para debugging
- ✅ **Tratamento de falhas** com `if: always()`

### **4. Relatórios**
- ✅ **Geração automática do Allure** via Maven plugin
- ✅ **Upload de múltiplos artifacts** (testes + relatórios)
- ✅ **Resumo de execução** com informações úteis

## 📋 Estrutura do Workflow

```yaml
name: API Transações Testes CI

on:
  push:                    # ✅ Executa em TODAS as branches
  pull_request:            # ✅ Executa em TODOS os PRs

jobs:
  test-java-21:
    runs-on: ubuntu-latest
    env:
      MONGO_URI: mongodb+srv://gillvanjs:KNAqSlJj0n6mawLV@cluster0.yia8ilv.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
      JWT_SECRET: KNAqSlJj0n6mawLV
      PORT: 3000

    steps:
    - Checkout code
    - Setup JDK 21
    - Cache Maven dependencies
    - Install banco-api dependencies
    - Start server with health check
    - Run tests
    - Generate Allure report
    - Upload artifacts
```

## 🔧 Configurações Avançadas

### **Execução Automática**
O workflow agora executa em:
- **TODAS as branches** quando há push
- **TODOS os pull requests** para qualquer branch

### **Cache Inteligente**
```yaml
- name: Cache Maven dependencies
  uses: actions/cache@v4
  with:
    path: ~/.m2
    key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
    restore-keys: ${{ runner.os }}-m2
```

### **Health Check do Servidor**
```yaml
- name: Wait for server to start
  run: |
    timeout 120 bash -c 'until curl -f http://localhost:3000/health || curl -f http://localhost:3000/; do sleep 5; done'
```

## 📊 Artifacts Gerados

### **1. Test Results**
- **Path**: `target/surefire-reports/`
- **Conteúdo**: Relatórios XML dos testes JUnit

### **2. Allure Results**
- **Path**: `target/allure-results/`
- **Conteúdo**: Dados brutos do Allure

### **3. Allure Report**
- **Path**: `target/site/allure-maven-plugin/`
- **Conteúdo**: Relatório HTML completo do Allure

## 🛠️ Comandos Maven Utilizados

### **Execução de Testes**
```bash
mvn -B clean test \
  -Dcucumber.filter.tags="@all" \
  -Dallure.results.directory=target/allure-results
```

### **Geração do Relatório**
```bash
mvn allure:report
```

## 🔍 Troubleshooting

### **Problema**: Secrets não encontrados
**Solução**: Verificar se os secrets estão configurados corretamente em Settings > Secrets and variables > Actions

### **Problema**: Servidor não inicia
**Solução**: Verificar logs em `banco-api.log` e validar se a porta 3000 está livre

### **Problema**: Testes falham
**Solução**: Verificar se o banco MongoDB está acessível e as credenciais estão corretas

### **Problema**: Allure não gera relatório**
**Solução**: Verificar se o plugin allure-maven está configurado no pom.xml

## 📈 Monitoramento

### **Métricas Importantes**
- **Tempo de execução** do workflow
- **Taxa de sucesso** dos testes
- **Cobertura de código** (se configurada)
- **Performance** do servidor

### **Alertas Recomendados**
- Falhas consecutivas no workflow
- Tempo de execução acima do esperado
- Falhas de conectividade com MongoDB

## 🎯 Próximos Passos

1. **Configurar os secrets** no GitHub
2. **Testar o workflow** com um push
3. **Verificar os artifacts** gerados
4. **Configurar notificações** (opcional)
5. **Monitorar execuções** regulares

## 📞 Suporte

Se encontrar problemas:
1. Verifique os logs do workflow
2. Valide a configuração dos secrets
3. Teste localmente primeiro
4. Consulte a documentação do GitHub Actions 