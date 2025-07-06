Feature: Serviço de Contas

  Rule: Consulta de Contas
    Scenario: Obter uma lista paginada de contas
      Given que o sistema possui várias contas cadastradas
      When a consulta de contas é realizada com um limite de 10 contas por página e na página 1
      Then o sistema retorna uma lista com no máximo 10 contas

  Rule: Consulta de Conta por ID
    Scenario: Obter detalhes de uma conta por ID
      Given que uma conta com o ID "123" existe
      When a consulta da conta com ID "123" é realizada
      Then o sistema retorna os detalhes da conta correspondente ao ID "123"
