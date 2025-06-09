Feature: Pagamento com Cartao de Credito
  Como um cliente
  Eu quero realizar pagamentos com cartao de credito
  Para finalizar minhas compras de forma segura

  @ATJ-001
  @all
  Scenario: Realizar pagamento com cartao de credito simples
    Given que eu tenho um cartao de credito valido
    When eu envio a requisicao de pagamento
    Then o pagamento deve ser processado com sucesso
    And o status code deve ser 200

  @ATJ-002
  @all
  Scenario: Realizar pagamento com cartao de credito autenticado
    Given que eu tenho um cartao de credito valido para autenticacao
    When eu envio a requisicao de pagamento autenticado
    Then o pagamento autenticado deve ser processado com sucesso
    And o status code deve ser 200

  @ATJ-003
  @all
  Scenario: Realizar pagamento com cartao de credito completo
    Given que eu tenho um cartao de credito valido com dados completos
    When eu envio a requisicao de pagamento completo
    Then o pagamento completo deve ser processado com sucesso
    And o status code deve ser 200 