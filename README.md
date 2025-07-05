# Automação de API REST - Cielo E-commerce

Este projeto contém testes automatizados para a API da Cielo E-commerce, utilizando Java, RestAssured e Cucumber.

## 🚀 Tecnologias Utilizadas

- Java 11
- Maven
- RestAssured
- Cucumber
- Lombok
- JUnit
- Jackson
- Allure Reports

## 📋 Pré-requisitos

- JDK 11 ou superior
- Maven
- IDE (recomendado: IntelliJ IDEA)
- Credenciais de Sandbox da Cielo (MerchantId e MerchantKey)
- **Allure CLI** (para geração de relatórios)

## 🔧 Configuração do Ambiente

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/AUTOMACAO-REST-API.git
```

2. Importe o projeto na sua IDE

3. Instale as dependências:
```bash
mvn clean install
```

4. **Instale o Allure CLI** (Windows):
   - Baixe o Allure CLI de: https://github.com/allure-framework/allure2/releases
   - Extraia o arquivo zip para uma pasta (ex: `C:\allure-2.24.0`)
   - Adicione o caminho `C:\allure-2.24.0\bin` ao PATH do sistema
   - Reinicie o terminal/IDE
   - Verifique a instalação: `allure --version`

## 📊 Relatórios Allure

### Geração Automatizada de Relatórios

O projeto inclui um sistema automatizado para gerar e visualizar relatórios Allure:

#### Script Batch Principal (`executa-tudo.bat`)
Este script executa os testes e gera o relatório automaticamente:

```bash
.\executa-tudo.bat
```

**Funcionalidades:**
- Executa todos os testes com `mvn test`
- Gera o relatório Allure com `mvn allure:report`
- Abre o relatório no navegador com `mvn allure:serve`
- Filtra mensagens de erro de porta em uso

#### Script para Abrir Relatório (`abrir-allure.bat`)
Script específico para abrir relatórios existentes sem mensagens de erro:

```bash
.\abrir-allure.bat
```

**Funcionalidades:**
- Tenta abrir o relatório em porta 56565
- Se falhar, tenta porta 8080
- Suprime mensagens de erro de porta em uso
- Fornece instruções para acesso manual

#### Controle de Ativação/Desativação

O relatório pode ser ativado ou desativado editando o arquivo `src/test/resources/allure.properties`:

```properties
# Para ativar o relatório automático
allure.enabled=true

# Para desativar o relatório automático (executa apenas os testes)
allure.enabled=false
```

**Importante:** Esta configuração funciona tanto para execução via script batch quanto via IDE.

### Comandos Allure CLI Manuais

#### Gerar Relatório
```bash
# Gerar relatório a partir dos resultados dos testes
allure generate target/allure-results --clean

# Gerar relatório em diretório específico
allure generate target/allure-results -o target/allure-reports/custom-report --clean
```

#### Abrir Relatório no Navegador
```bash
# Abrir relatório em porta específica (recomendado)
allure open target/allure-results -p 56565

# Abrir relatório em porta automática
allure open target/allure-results

# Abrir relatório gerado
allure open target/allure-reports/feature-03.07.2025
```

#### Servir Relatório (para compartilhamento)
```bash
# Servir relatório em porta específica
allure serve target/allure-results -p 56565

# Servir relatório em porta automática
allure serve target/allure-results
```

### Configurações do Allure

O projeto inclui configurações personalizadas do Allure:

#### Arquivo de Configuração (`src/test/resources/allure.properties`)
```properties
# Diretórios de resultados e relatórios
allure.results.directory=target/allure-results
allure.report.directory=target/allure-reports/feature-03.07.2025

# Integração com Cucumber
allure.cucumber.attach.console.log=true
allure.cucumber.attach.screenshot=true
allure.cucumber.attach.video=true

# Configurações de relatório
allure.report.issue.pattern=https://example.com/browse/{}
allure.report.tms.pattern=https://example.com/browse/{}

# Controle de ativação
allure.enabled=true

# Idioma
allure.language=pt

# Arquivos de configuração
allure.categories.file=src/test/resources/allure-categories.json
allure.environment.file=src/test/resources/allure-environment.properties
```

#### Categorias de Teste (`src/test/resources/allure-categories.json`)
Define categorias personalizadas para organizar os testes no relatório.

#### Ambiente (`src/test/resources/allure-environment.properties`)
Define informações do ambiente de teste para o relatório.

### Perfis Maven

O projeto usa perfis Maven para controlar a execução do Allure:

#### Perfil `no-allure` (padrão)
- **Ativação:** Automática por padrão
- **Comportamento:** Executa os testes sem gerar relatório Allure automaticamente
- **Uso:** `mvn test -P no-allure`

#### Perfil `with-allure`
- **Ativação:** Manual
- **Comportamento:** Executa os testes e gera relatório Allure automaticamente
- **Uso:** `mvn test -P with-allure`

### Plugins Automatizados

O projeto inclui plugins Java que automatizam o processo:

- **`AllureAutoReportPlugin`**: Executa automaticamente o script batch após os testes
- **`LogSummaryPlugin`**: Gera resumo dos logs de execução

### Fluxo de Execução Recomendado

1. **Execução Completa Automatizada:**
   ```bash
   .\executa-tudo.bat
   ```

2. **Execução pela IDE com Controle:**
   ```bash
   # Executar pela IDE respeitando allure.enabled
   .\executa-ide.bat
   
   # Ou executar diretamente com perfil específico
   mvn test -P no-allure    # Sem Allure
   mvn test -P with-allure  # Com Allure
   ```

3. **Execução Manual com Controle:**
   ```bash
   # Executar apenas os testes (sem Allure)
   mvn test -P no-allure
   
   # Executar testes com Allure
   mvn test -P with-allure
   
   # Gerar relatório
   mvn allure:report
   
   # Abrir relatório
   mvn allure:serve
   ```

4. **Execução com Allure CLI:**
   ```bash
   # Executar testes
   mvn test -P no-allure
   
   # Gerar e abrir relatório
   allure open target/allure-results -p 56565
   ```

### Troubleshooting

#### Problema: Comando `allure` não reconhecido
**Solução:**
1. Verifique se o Allure CLI está instalado
2. Confirme se o caminho está no PATH do sistema
3. Reinicie o terminal/IDE
4. Execute: `allure --version`

#### Problema: Browser não abre automaticamente
**Solução:**
- Use o Allure CLI: `allure open target/allure-results -p 56565`
- Ou acesse manualmente: `http://localhost:56565`

#### Problema: Porta em uso
**Solução:**
- Use uma porta diferente: `allure serve target/allure-results -p 8080`
- Ou mate o processo que está usando a porta
- Use o script `abrir-allure.bat` que tenta portas alternativas automaticamente

#### Problema: Mensagens de erro de porta em uso no log
**Solução:**
- As mensagens "Address already in use" e "Could not serve the report" são filtradas automaticamente pelos scripts
- Se ainda aparecerem, use o script `abrir-allure.bat` que suprime essas mensagens

## 🏗️ Estrutura do Projeto

```
src/
├── main/
│   └── java/
│       └── org/br/com/testes/
│           ├── config/
│           └── utils/
└── test/
    ├── java/
    │   └── org/br/com/testes/
    │       ├── controllers/
    │       │   └── usuarios/
    │       │       ├── MpBoletoController.java
    │       │       ├── MpCartaoDeCreditoController.java
    │       │       └── MpCartaoDeDebitoController.java
    │       ├── model/
    │       │   └── request/
    │       │       ├── BoletoRequest.java
    │       │       ├── CreditCardRequest.java
    │       │       └── DebitCardRequest.java
    │       └── steps/
    │           ├── MpBoletoSteps.java
    │           ├── MpCartaoDeCreditoSteps.java
    │           └── MpCartaoDeDebitoSteps.java
    └── resources/
        └── features/
            ├── boleto.feature
            ├── cartao_credito.feature
            └── cartao_debito.feature
```

## 🧪 Casos de Teste Implementados

### Cartão de Crédito
- CT-1001: Pagamento básico com cartão de crédito
- CT-1002: Pagamento autenticado com cartão de crédito
- CT-1003: Pagamento completo com cartão de crédito

### Cartão de Débito
- CT-2001: Pagamento básico com cartão de débito
- CT-2002: Pagamento autenticado com cartão de débito
- CT-2003: Pagamento completo com cartão de débito

### Boleto
- CT-3001: Pagamento com boleto básico

## 📝 Novos Casos de Teste a Implementar

### Cartão de Crédito
- CT-1004: Captura de transação após autorização (`Payment.Capture = false`)
- CT-1005: Cancelamento de transação via PaymentId
- CT-1006: Cancelamento de transação via MerchantOrderId
- CT-1007: Primeiro uso do cartão armazenado
- CT-1008: Recorrência com cartão já armazenado
- CT-1009: Transação com autenticação 3DS (genérico, cobrir fluxo básico)
- CT-1010: Transação com análise de fraude ClearSale
- CT-1011: Transação com análise de fraude CyberSource
- CT-1012: Transação com autenticação 3DS bem-sucedida (validar ECI de sucesso específico)
- CT-1013: Transação com falha ou negação na autenticação 3DS (validar ECI de falha/negação específico)
- CT-1014: Transação com autenticação 3DS Data Only (`Payment.ExternalAuthentication.DataOnly = true`)
- CT-1015: Transação Mastercard com indicador CIT (`Payment.InitiatedTransactionIndicator` para Cardholder-Initiated)
- CT-1016: Transação Mastercard com indicador MIT (`Payment.InitiatedTransactionIndicator` para Merchant-Initiated)
- CT-1017: Transação com captura automática bem-sucedida (`Payment.Capture = true`)
- CT-1018: Validação do campo `Payment.CreditCard.PaymentAccountReference` (PAR) em resposta bem-sucedida

### Cartão de Débito
- CT-2004: Cancelamento de transação via PaymentId
- CT-2005: Cancelamento de transação via MerchantOrderId
- CT-2006: Primeiro uso do cartão armazenado
- CT-2007: Transação com autenticação 3DS

### Boleto
- CT-3002: Validação de dados do boleto
- CT-3003: Validação de vencimento do boleto
- CT-3004: Validação de instruções do boleto

## 🔄 Endpoints da API

### Ambiente Sandbox
```
POST https://apisandbox.cieloecommerce.cielo.com.br/1/sales
```

### Ambiente Produção
```
POST https://api.cieloecommerce.cielo.com.br/1/sales
```

### Campos da Requisição (Cartão de Crédito)
- `MerchantOrderId`: Identificador único do pedido na loja.
- `Customer.Name`: Nome do comprador.
- `Payment.Type`: Tipo de pagamento (ex: "CreditCard").
- `Payment.Amount`: Valor do pagamento em centavos (ex: 10000 para R$100,00).
- `Payment.Installments`: Número de parcelas da transação.
- `Payment.SoftDescriptor`: Texto que aparecerá na fatura do cliente (máx. 13 caracteres).
- `Payment.Capture`: Booleano que indica se a transação deve ser capturada automaticamente (`true`) ou posteriormente (`false`).
- `Payment.CreditCard.CardNumber`: Número do cartão de crédito.
- `Payment.CreditCard.Holder`: Nome do portador impresso no cartão.
- `Payment.CreditCard.ExpirationDate`: Data de validade do cartão no formato MM/YYYY.
- `Payment.CreditCard.SecurityCode`: Código de segurança (CVV, CVC) do cartão.
- `Payment.CreditCard.Brand`: Bandeira do cartão (ex: "Visa", "Master").
- `Payment.InitiatedTransactionIndicator.Category`: Categoria do indicador de transação (CIT/MIT) para Mastercard.
- `Payment.InitiatedTransactionIndicator.SubCategory`: Subcategoria do indicador de transação (CIT/MIT) para Mastercard.
- `Payment.ExternalAuthentication.Cavv`: Valor de Autenticação do Portador do Cartão (Cardholder Authentication Verification Value) obtido do processo 3DS.
- `Payment.ExternalAuthentication.Xid`: ID da transação (Transaction ID) da autenticação 3DS.
- `Payment.ExternalAuthentication.Eci`: Indicador de Comércio Eletrônico (Electronic Commerce Indicator) da autenticação 3DS.
- `Payment.ExternalAuthentication.Version`: Versão do protocolo 3DS utilizada.
- `Payment.ExternalAuthentication.ReferenceID`: ID de referência do Directory Server (DS) 3DS.

### Campos da Resposta (Cartão de Crédito)
- `Payment.PaymentId`: Número de identificação único do pagamento gerado pela Cielo. Essencial para consultas, capturas e cancelamentos.
- `Payment.Tid`: Identificador da transação na adquirente (Terminal ID).
- `Payment.ProofOfSale`: Número da autorização da transação, também conhecido como NSU (Número Sequencial Único).
- `Payment.AuthorizationCode`: Código de autorização da transação, retornado pelo emissor do cartão.
- `Payment.Status`: Código numérico que representa o status da transação (ex: `1` para Autorizada, `2` para Pagamento Confirmado/Capturada, `0` para Pendente).
- `Payment.ReturnCode`: Código de retorno específico da adquirente ou do banco emissor.
- `Payment.ReturnMessage`: Mensagem descritiva associada ao `ReturnCode`.
- `Payment.CreditCard.PaymentAccountReference`: PAR (Payment Account Reference) é um valor que identifica de forma única a conta do cartão. Retornado pelas bandeiras Visa e Mastercard.
- `Payment.MerchantAdviceCode`: Código de Aconselhamento do Comerciante, específico da Mastercard, que pode indicar ações para retentativas.
- `Payment.CreditCard.CardToken`: Token do cartão, retornado se a transação resultou na criação/atualização de um token ou se um token foi utilizado.
- `Links`: Array de links HATEOAS que podem ser utilizados para ações futuras relacionadas à transação (ex: realizar captura, cancelamento), se aplicável e retornado pela API.

## 📦 Dependências Principais

```xml
<dependencies>
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.5.0</version>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.20.0</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.36</version>
    </dependency>
</dependencies>
```

## ⚠️ Observações Importantes

1. **Captura da Transação**:
   - A captura pode ser automática ou posterior.
   - Para captura automática, envie `Payment.Capture = true`.
   - Para captura posterior, envie `Payment.Capture = false` e realize a [captura posteriormente](https://docs.cielo.com.br/ecommerce-cielo/reference/capturar-apos-autorizacao).

2. **Valor Zero**: Não é possível realizar transações com valor 0. Para validar cartões, use o Zero Auth.

3. **Mastercard Credenciais Armazenadas (CIT/MIT)**: 
   - Para transações (crédito e débito) com cartões Mastercard armazenados, é obrigatório o envio do Indicador de Início da Transação.
   - Este indicador diferencia transações iniciadas pelo portador (CIT - Cardholder-Initiated Transaction) das iniciadas pelo lojista (MIT - Merchant-Initiated Transaction).
   - Deve-se incluir o nó `Payment.InitiatedTransactionIndicator` com os parâmetros `Category` e `SubCategory`. Consulte a [documentação para detalhes](https://docs.cielo.com.br/ecommerce-cielo/page/indicador-de-início-da-transação-cit-e-mit).

4. **Taxa de Serviço**: 
   - O campo `Payment.ServiceTaxAmount` é exclusivo para empresas aéreas, permitindo a cobrança da taxa de embarque separadamente da passagem.

5. **Identificação do Pedido**:
   - O `MerchantOrderId` não se altera durante o fluxo transacional.
   - Um novo `MerchantOrderId` pode ser gerado pela Cielo se o enviado:
     - Estiver fora das especificações.
     - Já tiver sido utilizado nas últimas 24 horas.
   - Para fins de conciliação, utilize o `TransactionId` (TID).

6. **Autenticação 3DS**:
   - Os dados recebidos do script de autenticação 3DS devem ser informados no nó `Payment.ExternalAuthentication`.
   - Em transações 3DS Data Only, defina `Payment.ExternalAuthentication.DataOnly = true`.
   - A validação da autenticação (ECI) deve ser feita considerando o ECI retornado fora do nó `Payment.ExternalAuthentication`.

## 🔄 CI/CD

O projeto utiliza GitHub Actions para execução automática dos testes em cada push e pull request.

## 🤝 Contribuindo

1. Faça um Fork do projeto
2. Crie uma Branch para sua Feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a Branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## ✒️ Autores

* **Seu Nome** - *Desenvolvimento* - [seu-usuario](https://github.com/seu-usuario)

## 📞 Suporte

Para suporte, envie um email para seu-email@exemplo.com ou abra uma issue no projeto.
