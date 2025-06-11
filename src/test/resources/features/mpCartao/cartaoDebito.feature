@TransaçãoDebito
Feature: Pagamento com Cartão de Débito
  Como um usuário do sistema
  Eu quero realizar um pagamento com cartão de débito
  Para que eu possa finalizar minha compra

  @CT-2001
  Scenario: Realizar pagamento com cartão de débito básico
    Given que eu tenho um cartão de débito válido
    When eu envio a requisição de pagamento com débito
    Then o pagamento com débito deve ser processado com sucesso
    And o status code do pagamento com débito deve ser 201

  @CT-2002
  Scenario: Realizar pagamento com cartão de débito autenticado
    Given que eu tenho um cartão de débito válido para autenticação
    When eu envio a requisição de pagamento com débito autenticado
    Then o pagamento com débito deve ser processado com sucesso
    And o status code do pagamento com débito deve ser 201

  @CT-2003
  Scenario: Realizar pagamento com cartão de débito completo
    Given que eu tenho um cartão de débito válido com dados completos
    When eu envio a requisição de pagamento com débito completo
    Then o pagamento com débito deve ser processado com sucesso
    And o status code do pagamento com débito deve ser 201 