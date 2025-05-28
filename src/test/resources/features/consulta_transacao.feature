@ConsultaTransacao
Feature: Consulta de Transação de Cartão de Crédito
  Como um usuário do sistema
  Eu quero consultar uma transação de cartão de crédito
  Para que eu possa verificar o status do pagamento

  @CT-1004
  Scenario: Consultar transação de cartão de crédito
    Given que eu tenho um PaymentId válido
    When eu envio a requisição de consulta
    Then a transação deve ser consultada com sucesso
    And o status code da consulta deve ser 200 