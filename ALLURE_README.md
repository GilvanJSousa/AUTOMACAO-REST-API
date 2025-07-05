# Guia Completo de Integração Allure no Projeto AUTOMACAO-REST-API

## Visão Geral do Allure
O Allure é uma poderosa ferramenta de geração de relatórios para testes automatizados, suportando múltiplos frameworks (JUnit, Cucumber, TestNG, etc). Ele permite visualizar cenários, steps, evidências, anexos e métricas de execução de forma visual e interativa.

## Estrutura do Projeto para Integração
```
AUTOMACAO-REST-API/
├── pom.xml
├── src/
│   └── test/
│       ├── java/
│       │   └── org/br/com/testes/
│       │       ├── steps/           # Steps do Cucumber
│       │       ├── controllers/     # Lógica de negócio dos testes
│       │       ├── utils/           # Plugins, formatadores, helpers
│       │       │   └── AllureAutoReportPlugin.java
│       │       └── ExecucaoTestes.java # Runner principal
│       └── resources/
│           ├── features/            # Features do Cucumber
│           ├── allure.properties    # Configuração principal do Allure
│           ├── allure-categories.json # Categorização de falhas
│           └── allure-environment.properties # Configuração do ambiente
├── allure-report/                   # Relatório gerado
├── target/allure-results/           # Resultados brutos dos testes
├── executa-tudo.bat                 # Script de execução automática
├── abrir-allure.bat                 # Script para abrir relatório
└── ...
```

## Passo a Passo para Configuração

### 1. Dependências no `pom.xml`
Inclua as dependências do Allure e Cucumber:
```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-cucumber7-jvm</artifactId>
    <version>2.24.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-junit4</artifactId>
    <version>2.24.0</version>
    <scope>test</scope>
</dependency>
```

### 2. Plugins Maven
No bloco `<build><plugins>`, adicione:
```xml
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.12.0</version>
    <configuration>
        <reportVersion>2.24.0</reportVersion>
        <resultsDirectory>${project.build.directory}/allure-results</resultsDirectory>
        <reportDirectory>${project.build.directory}/allure-report</reportDirectory>
    </configuration>
</plugin>
```

### 3. Configuração do Runner Cucumber
No runner (ex: `ExecucaoTestes.java`):
```java
@CucumberOptions(
    plugin = {
        "org.br.com.testes.utils.LogSummaryPlugin",
        "org.br.com.testes.utils.AllureAutoReportPlugin",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    ...
)
```
- O plugin oficial do Allure gera os arquivos de resultado.
- O plugin customizado abre o relatório automaticamente.

## Arquivos de Configuração

### 1. `src/test/resources/allure.properties`
Arquivo principal de configuração do Allure:
```properties
# Allure Configuration
allure.results.directory=target/allure-results

# Cucumber Integration
allure.cucumber.attach.console.log=true
allure.cucumber.attach.screenshot=true
allure.cucumber.attach.video=true

# Report Configuration
allure.report.issue.pattern=https://example.com/browse/{}
allure.report.tms.pattern=https://example.com/browse/{}

allure.enabled=true

# Language Configuration
allure.language=pt

# Categories Configuration
allure.categories.file=src/test/resources/allure-categories.json

# Environment Configuration
allure.environment.file=src/test/resources/allure-environment.properties
```

**Explicação das propriedades:**
- `allure.results.directory`: Diretório onde ficam os resultados brutos
- `allure.cucumber.attach.*`: Configurações de anexos (logs, screenshots, vídeos)
- `allure.report.issue.pattern`: Padrão para links de issues (Jira, etc.)
- `allure.enabled`: Habilita/desabilita o Allure globalmente
- `allure.language`: Idioma do relatório
- `allure.categories.file`: Arquivo de categorização de falhas
- `allure.environment.file`: Arquivo de configuração do ambiente

### 2. `src/test/resources/allure-categories.json`
Define categorias para organizar falhas no relatório:
```json
[
  {
    "name": "Falhas de Teste",
    "matchedStatuses": ["failed"]
  },
  {
    "name": "Testes Ignorados",
    "matchedStatuses": ["skipped"]
  },
  {
    "name": "Problemas de Configuracao",
    "matchedStatuses": ["broken"]
  },
  {
    "name": "Testes Passaram",
    "matchedStatuses": ["passed"]
  }
]
```

**Como personalizar categorias:**
- `name`: Nome da categoria que aparece no relatório
- `matchedStatuses`: Status dos testes que pertencem à categoria
- Status disponíveis: `failed`, `broken`, `skipped`, `passed`

### 3. `src/test/resources/allure-environment.properties`
Configurações específicas do ambiente de teste:
```properties
# Environment Configuration
Environment=Test
Browser=API
Platform=REST API
Version=1.0
Framework=Cucumber + Rest Assured
Language=Java 21
allure.enabled=true
```

**Como personalizar para diferentes ambientes:**
```properties
# Para ambiente de desenvolvimento
Environment=Development
BaseURL=https://dev-api.example.com

# Para ambiente de produção
Environment=Production
BaseURL=https://api.example.com
```

## Arquivos .bat e Automação

### 1. `executa-tudo.bat`
Script principal para execução automática completa:
```batch
@echo off
setlocal EnableDelayedExpansion

rem Caminho do arquivo de propriedades
set PROP_FILE=src\test\resources\allure.properties
set ENABLED=

rem Lê a propriedade allure.enabled
for /f "usebackq tokens=1,2 delims== " %%a in ("%PROP_FILE%") do (
    if /i "%%a"=="allure.enabled" set ENABLED=%%b
)

rem Se não encontrou a propriedade, assume true (Allure ativado por padrão)
if "%ENABLED%"=="" set ENABLED=true

if /i "%ENABLED%"=="true" (
    echo [INFO] Allure ativado. Executando testes e relatório...
    mvn test -P with-allure
    mvn allure:report
    echo [INFO] Tentando abrir relatório Allure...
    mvn allure:serve 2>&1 | findstr /v "Address already in use\|Could not serve the report\|java.net.BindException"
) else (
    echo [INFO] Allure desativado em allure.properties. Executando apenas os testes...
    mvn test -P no-allure
)

endlocal
exit /b
```

**Funcionalidades do script:**
- Lê a configuração `allure.enabled` do arquivo properties
- Executa testes com perfil `with-allure` se habilitado
- Gera relatório automaticamente
- Abre o relatório no navegador
- Trata erros de porta em uso

### 2. `abrir-allure.bat`
Script para abrir relatório existente:
```batch
@echo off
echo [INFO] Abrindo relatório Allure...

rem Tenta abrir o relatório em porta específica
allure open target/allure-results -p 56565 2>&1 | findstr /v "Address already in use\|Could not serve the report\|java.net.BindException\|Starting web server\|at io.qameta.allure.Commands"

if %ERRORLEVEL% NEQ 0 (
    echo [INFO] Porta 56565 em uso, tentando porta alternativa...
    allure open target/allure-results -p 8080 2>&1 | findstr /v "Address already in use\|Could not serve the report\|java.net.BindException\|Starting web server\|at io.qameta.allure.Commands"
)

echo [INFO] Relatório Allure disponível. Verifique se o navegador abriu automaticamente.
echo [INFO] Se não abriu, acesse manualmente: http://localhost:56565 ou http://localhost:8080
```

**Funcionalidades do script:**
- Tenta abrir na porta 56565 primeiro
- Se falhar, tenta na porta 8080
- Filtra mensagens de erro desnecessárias
- Fornece URLs alternativas se não abrir automaticamente

## Processo Automático Completo

### Fluxo de Execução
1. **Execução dos Testes:**
   ```bash
   # Via script .bat
   executa-tudo.bat
   
   # Via Maven direto
   mvn test -P with-allure
   ```

2. **Geração de Resultados:**
   - Os testes executam e geram arquivos JSON em `target/allure-results/`
   - Cada teste gera: `result.json`, `container.json`, `attachments/`

3. **Geração do Relatório:**
   - O plugin `AllureAutoReportPlugin` executa automaticamente
   - Roda `allure serve target/allure-results`
   - Captura a URL do servidor
   - Abre o navegador automaticamente

4. **Visualização:**
   - Relatório interativo disponível no navegador
   - Dados organizados por categorias
   - Logs, anexos e métricas disponíveis

### Como Criar e Organizar as Classes de Teste para Allure
- **Steps:** Devem ser simples, apenas chamando métodos das controllers/lógicas.
- **Controllers:** Toda lógica de negócio dos testes.
- **Utils:** Plugins, formatadores, helpers (ex: LogFormatter, AllureAutoReportPlugin).
- **Features:** Devem estar em `src/test/resources/features`.

### Exemplo de Step
```java
@When("realizo um pagamento com cartao de credito valido")
public void realizoPagamentoCartaoCreditoValido() {
    pagamentoController.realizarPagamentoValido();
}
```

### Exemplo de Controller
```java
public class PagamentoController {
    public void realizarPagamentoValido() {
        // Lógica de chamada à API, validação, logs, etc.
        LogFormatter.logStep("Realizando pagamento valido");
        // ...
    }
}
```

## Como Funciona o Plugin Customizado (`AllureAutoReportPlugin`)
- Executa automaticamente ao final dos testes.
- Roda o comando `allure serve target/allure-results`.
- Captura a URL do servidor Allure e abre no navegador.
- Garante que o navegador só será aberto uma vez.
- Se já houver navegador aberto para a URL, não abre novamente.
- Todos os logs de debug estão comentados para output limpo.

### Trecho principal:
```java
if (serverUrl != null && !serverUrl.isEmpty()) {
    if (!isBrowserAlreadyOpen(serverUrl)) {
        ProcessBuilder pbBrowser = new ProcessBuilder("cmd", "/c", "start " + serverUrl);
        pbBrowser.start();
        System.out.println("[INFO] AllureAutoReportPlugin: Relatório Allure disponível em " + serverUrl);
    }
}
```

## Como Executar e Visualizar o Relatório

### Método 1: Script Automático (Recomendado)
```bash
# Execute o script principal
executa-tudo.bat
```

### Método 2: Maven Direto
```bash
# Executar testes com Allure
mvn test -P with-allure

# Gerar relatório
mvn allure:report

# Abrir relatório
mvn allure:serve
```

### Método 3: CLI Allure
```bash
# Abrir relatório existente
allure serve target/allure-results

# Gerar relatório estático
allure generate target/allure-results --clean -o allure-report
```

## Dicas de Troubleshooting

### Problemas Comuns e Soluções

#### 1. Relatório Abre Vazio
**Sintoma:** Navegador abre mas sem dados dos testes
**Solução:** 
- Verifique se existem arquivos em `target/allure-results/`
- Execute `mvn clean test` para limpar e regenerar
- Verifique se o plugin oficial do Allure está configurado

#### 2. Navegador Abre Duas Vezes
**Sintoma:** Duas abas/janelas do relatório
**Solução:**
- O plugin customizado já trata isso automaticamente
- Se persistir, verifique se há outros scripts .bat executando

#### 3. Erro de Porta em Uso
**Sintoma:** `Address already in use` ou `BindException`
**Solução:**
- Feche servidores Allure antigos: `taskkill /f /im java.exe`
- Use o script `abrir-allure.bat` que tenta portas alternativas
- Mude a porta manualmente: `allure serve target/allure-results -p 8080`

#### 4. Erro "Could not add attachment: no test is running"
**Sintoma:** Erro nos logs durante execução
**Solução:**
- O plugin oficial do Allure é obrigatório
- Mantenha `"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"` na configuração

#### 5. Relatório Não Gera
**Sintoma:** Nenhum arquivo em `target/allure-results/`
**Solução:**
- Verifique se `allure.enabled=true` no properties
- Execute `mvn clean` antes dos testes
- Verifique se os testes estão passando

### Logs de Debug
Para ativar logs detalhados, descomente as linhas de debug no `AllureAutoReportPlugin.java`:
```java
// System.out.println("[DEBUG] AllureAutoReportPlugin: runAllureCli chamado!");
```

## Boas Práticas
- **Nunca duplique steps**: Steps genéricos só se o comportamento for idêntico.
- **Logs:** Use sempre o `LogFormatter.logStep` para logs de steps, sem acentos.
- **Organização:** Mantenha controllers, steps e utils bem separados.
- **Features:** Sem acentos nos nomes dos arquivos e conteúdo.
- **Estrutura de pacotes:**
  - `org.br.com.testes.steps` para steps
  - `org.br.com.testes.controllers` para lógica
  - `org.br.com.testes.utils` para plugins/helpers

## Observações sobre Integração com Cucumber
- O plugin oficial do Allure (`io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm`) é obrigatório para geração dos arquivos de resultado.
- O plugin customizado (`AllureAutoReportPlugin`) é responsável apenas por abrir o relatório automaticamente.
- Não misture lógica de negócio nos steps.
- Não use Map como parâmetro em steps (preferir parâmetros diretos).

## Como Customizar o Plugin

### 1. Mudar Navegador Padrão
```java
// Para Chrome
ProcessBuilder pbBrowser = new ProcessBuilder("cmd", "/c", "start chrome " + serverUrl);

// Para Firefox
ProcessBuilder pbBrowser = new ProcessBuilder("cmd", "/c", "start firefox " + serverUrl);
```

### 2. Mudar Timeout de Espera
```java
// Aguarda 5 segundos em vez de 3
Thread.sleep(5000);
```

### 3. Adicionar Logs Detalhados
```java
// Descomente as linhas de debug
System.out.println("[DEBUG] AllureAutoReportPlugin: runAllureCli chamado!");
```

### 4. Personalizar Categorias
Edite `allure-categories.json`:
```json
[
  {
    "name": "Falhas de API",
    "matchedStatuses": ["failed"],
    "messageRegex": ".*API.*"
  }
]
```

### 5. Configurar Links para Issues
No `allure.properties`:
```properties
allure.report.issue.pattern=https://jira.company.com/browse/{}
allure.report.tms.pattern=https://testrail.company.com/cases/{}
```

## Configuração para Diferentes Ambientes

### Desenvolvimento
```properties
# allure-environment.properties
Environment=Development
BaseURL=https://dev-api.example.com
Database=dev-db
```

### Homologação
```properties
# allure-environment.properties
Environment=Homologation
BaseURL=https://hml-api.example.com
Database=hml-db
```

### Produção
```properties
# allure-environment.properties
Environment=Production
BaseURL=https://api.example.com
Database=prod-db
```

## Integração com CI/CD

### Jenkins Pipeline
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test -P with-allure'
            }
        }
        stage('Generate Report') {
            steps {
                sh 'mvn allure:report'
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'allure-report',
                    reportFiles: 'index.html',
                    reportName: 'Allure Report'
                ])
            }
        }
    }
}
```

### GitHub Actions
```yaml
name: Tests with Allure
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 21
      uses: actions/setup-java@v2
      with:
        java-version: '21'
    - name: Run tests
      run: mvn test -P with-allure
    - name: Generate Allure Report
      run: mvn allure:report
    - name: Upload Allure Report
      uses: actions/upload-artifact@v2
      with:
        name: allure-report
        path: allure-report/
```

## Referências Úteis
- [Documentação oficial do Allure](https://docs.qameta.io/allure/)
- [Allure Maven Plugin](https://docs.qameta.io/allure/#_maven)
- [Exemplo de integração Cucumber + Allure](https://github.com/allure-framework/allure-cucumber)
- [Documentação oficial da API Cielo](https://docs.cielo.com.br/ecommerce-cielo/reference/sobre-a-api)
- [Allure Categories](https://docs.qameta.io/allure/#_categories)
- [Allure Environment](https://docs.qameta.io/allure/#_environment)

---

**Este guia serve como referência completa para onboarding, troubleshooting e manutenção da integração Allure neste projeto. Todos os exemplos de código são copiáveis e prontos para uso.** 