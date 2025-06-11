@Cancelamento
Feature: Cancelamento de Transacoes
  Como um usuário do sistema
  Eu quero cancelar uma transação
  Para reverter uma operação quando necessário

  Background: Realizar pagamento com cartão de crédito completo
    Given que eu tenho um cartão de crédito válido com dados completos
    When eu envio a requisição de pagamento completo
    Then o pagamento completo deve ser processado com sucesso
    And o status code deve ser 201

  @CT-4001
  Scenario: Cancelar transacao por PaymentId
    Given que estou na pagina de cancelamento
    When realizo o cancelamento da transacao por PaymentId
    Then valido que o cancelamento foi realizado com sucesso
    And valido a API Cancelar com o status code 200

  @CT-4002
  Scenario: Cancelar transacao por MerchantOrderId
    Given que estou na pagina de cancelamento
    When realizo o cancelamento da transacao por MerchantOrderId
    Then valido que o cancelamento foi realizado com sucesso
    And valido a API Cancelar com o status code 200

  @CT-4003
  Scenario: Cancelar transacao parcial por PaymentId
    Given que estou na pagina de cancelamento
    When realizo o cancelamento parcial da transacao por PaymentId
    Then valido que o cancelamento parcial foi realizado com sucesso
    And valido a API Cancelar com o status code 200