@login
Feature: Validar Login
  Como usuario do sistema
  Eu quero realizar login
  para gerar o accessToken

  @@TEST_QAR-1547
  Scenario: Validar login com sucesso
    Given envio uma solicitação POST de login como Admin
    Then valido o API Login com status code 200

  @@TEST_QAR-1548
  Scenario: Validar login com sucesso
    Given envio uma solicitação POST de login como Usuario
    Then valido o API Login com status code 200