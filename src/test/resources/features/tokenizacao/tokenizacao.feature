@Tokenizacao
Feature: Tokenizacao de Cartao
  Como um cliente
  Eu quero tokenizar meu cartao de credito
  Para realizar pagamentos de forma segura

  @CriarCartaoTokenizado @CT-5001
  Scenario: Tokenizar cartao de credito com sucesso
    Given que tenho os dados do cartao de credito
    When envio a requisicao de tokenizacao
    Then valido que o cartao foi tokenizado com sucesso
    And valido o status code 201 para 'API de tokenizacao'
