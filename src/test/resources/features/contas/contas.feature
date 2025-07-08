@contas
  Feature: Validar as contas bancárias
  Eu
  Como
  Para

  @contasBancarias
  Scenario: Validar a lista de contas bancarias
    Given que envio a requisição de listar contas bancarias
    Then a API listar contas bancarias deve retornar status code 200

  @contasObeterDetalhesContaBancaria
  Scenario Outline: Validar obter detalhes de uma conta bancaria
    Given que envio a requisicao de GET Obter detalhes de uma conta "<CONTA>" bancaria
    Then a API listar contas bancarias deve retornar status code 200

    Examples:
    |    CONTA                 |
    | 6867c26d12ba0eba945873a5 |
    | 6867c26d12ba0eba945873a6 |
    | 6867c26d12ba0eba945873a7 |
    | 6867c26d12ba0eba945873a8 |
