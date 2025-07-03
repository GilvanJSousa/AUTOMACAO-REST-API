Feature: Validar Login
  Como usuario do sistema
  Eu quero realizar login
  para gerar o accessToken

  @login
  Scenario: Validar login com sucesso
    Given envio uma solicitação POST de login
    Then valido o API Login com status coide 200