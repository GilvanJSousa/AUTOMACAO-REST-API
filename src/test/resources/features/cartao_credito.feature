@Transação
Feature: Pagamento com Cartão de Crédito
  Como um usuário do sistema
  Eu quero realizar um pagamento com cartão de crédito
  Para que eu possa finalizar minha compra

  @CT-1001
  Scenario: : Realizar pagamento com cartão de crédito válido
    Given que eu tenho um cartão de crédito válido
    When eu envio a requisição de pagamento
    Then o pagamento deve ser processado com sucesso
    And o status code deve ser 201 