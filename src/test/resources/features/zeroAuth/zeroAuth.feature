@zeroAuth
Feature: Teste de ZeroAuth
  Como um usuário do sistema
  Eu quero validar um cartão de crédito
  Para garantir que o cartão é válido antes do pagamento

  Background: Tokenizar cartao de credito com sucesso
    Given que tenho os dados do cartao de credito
    When envio a requisicao de tokenizacao
    Then valido que o cartao foi tokenizado com sucesso
    And valido o status code 201 para 'API de tokenizacao'

  @zeroAuthCardToken
  Scenario: Validar cartão de crédito com ZeroAuth
    Given que estou na pagina de validacao de cartao
    When realizo a validacao do cartao de credito
    Then valido que o cartao foi validado com sucesso
    And o status code do 'ZeroAuth' ser 200

  @zeroAuthDebitCard
  Scenario: Validar cartão de débito com ZeroAuth
    Given que estou na pagina de validacao de cartao
    When realizo a validacao do cartao de debito
    Then valido que o cartao de debito foi validado com sucesso
    And o status code do 'ZeroAuth' ser 200

  @zeroAuth @zeroAuthAvs
  Scenario: Validar cartão de crédito com AVS
    Given que estou na pagina de validacao de cartao
    When realizo a validacao do cartao com AVS
    Then valido que o cartao com AVS foi validado com sucesso
    And o status code do 'ZeroAuth' ser 200 