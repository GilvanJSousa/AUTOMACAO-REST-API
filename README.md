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

## 📋 Pré-requisitos

- JDK 11 ou superior
- Maven
- IDE (recomendado: IntelliJ IDEA)
- Credenciais de Sandbox da Cielo (MerchantId e MerchantKey)

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
