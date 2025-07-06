@contas
  Feature: Validar as contas bancárias
  Eu
  Como
  Para

  @contasBancarias
  Scenario: Validar a lista de contas bancarias
    Given que envio a requisição de listar contas bancarias
    Then a API listar contas bancarias deve retornar status code 200