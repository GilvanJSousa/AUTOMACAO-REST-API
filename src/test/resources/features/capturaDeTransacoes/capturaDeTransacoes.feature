@Captura
Feature: Captura de Transacoes
  Como um usuário do sistema
  Eu quero capturar uma transação
  Para confirmar uma operação quando necessário

  Background: Realizar pagamento com cartão de crédito completo
    Given que eu tenho um cartão de crédito válido com dados completos
    When eu envio a requisição de pagamento completo
    Then o pagamento completo deve ser processado com sucesso
    And o status code deve ser 201

  @CT-5001
  Scenario: Capturar transacao por PaymentId
    Given que estou na pagina de captura
    When realizo a captura da transacao por PaymentId
    Then valido que a captura foi realizada com sucesso
    And valido a API Capturar com o status code 200

  @CT-5002
  Scenario: Capturar transacao parcial por PaymentId
    Given que estou na pagina de captura
    When realizo a captura parcial da transacao por PaymentId
    Then valido que a captura parcial foi realizada com sucesso
    And valido a API Capturar com o status code 200
