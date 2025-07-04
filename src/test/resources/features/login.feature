@login
Feature: Validar Login
  Como usuario do sistema
  Eu quero realizar login
  para gerar o accessToken

  @loginAdmin
  Scenario: Validar login com sucesso
    Given envio uma solicitação POST de login como Admin
    Then valido o API Login com status code 200

  @loginUsuario
  Scenario: Validar login com sucesso
    Given envio uma solicitação POST de login como Usuario
    Then valido o API Login com status code 200