@all @TransaçãoCredito
Feature: Pagamento com Cartão de Crédito
  Como um usuário do sistema
  Eu quero realizar um pagamento com cartão de crédito
  Para que eu possa finalizar minha compra

  @TransaçãoCreditoMinimo @CT-1001
  Scenario: Realizar pagamento com cartão de crédito minimo
    Given que eu tenho um cartão de crédito válido
    When eu envio a requisição de pagamento
    Then o pagamento deve ser processado com sucesso
    And o status code deve ser 201

  @TransacaoCreditoAutenticado @CT-1002
  Scenario: Realizar pagamento com cartão de crédito autenticado
    Given que eu tenho um cartão de crédito válido para autenticação
    When eu envio a requisição de pagamento autenticado
    Then o pagamento autenticado deve ser processado com sucesso
    And o status code deve ser 201

  @TransacaoCreditoCompleto @CT-1003
  Scenario: Realizar pagamento com cartão de crédito completo
    Given que eu tenho um cartão de crédito válido com dados completos
    When eu envio a requisição de pagamento completo
    Then o pagamento completo deve ser processado com sucesso
    And o status code deve ser 201 