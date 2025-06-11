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

  @ConsultarCartaoTokenizado @CT-5002
  Scenario: Consultar cartao tokenizado com sucesso
    Given que tenho o token do cartao
    When envio a requisicao de consulta do cartao tokenizado
    Then valido que os dados do cartao foram retornados com sucesso
    And valido o status code 200 para 'API de consulta de cartao tokenizado'

  @RealizarPagamentoComCartaoTokenizado @CT-5003
  Scenario: Realizar pagamento com cartao tokenizado com sucesso
    Given que tenho o token do cartao
    And que tenho os dados do pagamento
    When envio a requisicao de pagamento com cartao tokenizado
    Then valido que o pagamento foi realizado com sucesso
    And valido o status code 201 para 'API de pagamento'

  @RealizarPagamentoComCartaoTokenizadoAmex @CT-5004
  Scenario: Realizar pagamento com cartao tokenizado American Express com sucesso
    Given que tenho o token do cartao American Express
    And que tenho os dados do pagamento com cartao American Express
    When envio a requisicao de pagamento com cartao tokenizado
    Then valido que o pagamento foi realizado com sucesso
    And valido o status code 201 para 'API de pagamento'

  @RealizarPagamentoComCartaoTokenizadoVisa @CT-5005
  Scenario: Realizar pagamento com cartao tokenizado Visa com sucesso
    Given que tenho o token do cartao Visa
    And que tenho os dados do pagamento com cartao Visa
    When envio a requisicao de pagamento com cartao tokenizado
    Then valido que o pagamento foi realizado com sucesso
    And valido o status code 201 para 'API de pagamento'
