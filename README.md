# Automação de API REST - Cielo E-commerce

Este projeto contém testes automatizados para a API da Cielo E-commerce, utilizando Java, RestAssured e Cucumber.

## 🚀 Tecnologias Utilizadas

- Java 21
- Cucumber
- JUnit
- RestAssured
- Lombok
- Maven

## 📋 Pré-requisitos

- Java JDK 21
- Maven
- Node.js (para rodar o Serverest localmente)
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
- CT-1004: Captura de transação após autorização
- CT-1005: Cancelamento de transação via PaymentId
- CT-1006: Cancelamento de transação via MerchantOrderId
- CT-1007: Primeiro uso do cartão armazenado
- CT-1008: Recorrência com cartão já armazenado
- CT-1009: Transação com autenticação 3DS
- CT-1010: Transação com análise de fraude ClearSale
- CT-1011: Transação com análise de fraude CyberSource

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

1. **Valor Zero**: Não é possível realizar transações com valor 0. Para validar cartões, use o Zero Auth.

2. **Mastercard Credenciais Armazenadas**: 
   - Requer envio do Indicador de Início da Transação
   - Necessário para compras com cartão armazenado
   - Deve incluir nó `InitiatedTransactionIndicator` com `Category` e `SubCategory`

3. **Taxa de Serviço**: 
   - Campo `Payment.ServiceTaxAmount` exclusivo para empresas aéreas
   - Permite cobrar taxa de embarque separadamente

4. **Identificação do Pedido**:
   - `MerchantOrderId` não altera durante o fluxo
   - Novo número pode ser gerado se:
     - ID fora das especificações
     - ID já utilizado nas últimas 24 horas

5. **Autenticação 3DS**:
   - Dados recebidos devem ser informados no nó `Payment.ExternalAuthentication`
   - Para 3DS Data Only, usar `ExternalAuthentication.DataOnly = true`
   - Validar autenticação pelo ECI fora do nó `Payment.ExternalAuthentication`

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
