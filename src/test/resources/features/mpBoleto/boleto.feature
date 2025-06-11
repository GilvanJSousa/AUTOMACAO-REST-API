@TransaçãoBoleto
Feature: Pagamento com Boleto
  Como um usuário do sistema
  Eu quero realizar um pagamento com boleto
  Para que eu possa finalizar minha compra

  @pagamentoBoleto
  Scenario: Realizar pagamento com boleto básico
    Given que eu tenho um boleto válido
    When eu envio a requisição de pagamento com boleto
    Then o pagamento com boleto deve ser processado com sucesso
    And o status code do pagamento com boleto deve ser 201 