@transferencia
Feature: Validar funcionalidade de Transferencias entre contas
  Como usuario do sistema
  Eu quero realizar transferencias
  Para

    @CT-1001 @TransferenciaContas
    Scenario: Validar transferencia entre contas
      Given que envio a requisicao de POST para realizar uma transferencia no valor de R$ 11.00 entre contas
      Then a API Transferencia deve retornar o status code 201

  @CT-1002 @TransferenciasLista
  Scenario: Validar a lista das transferencias realizadas
    Given que envio a requisicao de GET para Listar as transferencias realizadas
    Then a API Transferencia deve retornar o status code 200

  @CT-1003 @TransferenciasEspecifica
   Scenario: Validar consulta de transferencia especifica
     Given que envio a requisicao de GET para consultar transferencia especifica
     Then a API Transferencia deve retornar o status code 200

  @CT-1004 @TransferenciaAtualizacaoCompleta
   Scenario: Validar atualizacao completa de uma transferencia
     Given que envio uma requisicao PUT atualizar completamente o valor R$ 12.00 da transferencia
     Then a API Transferencia deve retornar o status code 204

  @CT-1005 @TransferenciaParcial
   Scenario: Validar atualizacao parcial de uma transferencia
     Given que envio uma requisicao PATCH atualiza parcialmente em valor R$ 13.00 da transferencia
     Then a API Transferencia deve retornar o status code 204

  @CT-1006 @TransferenciaParcial
  Scenario: Validar Remocao de uma transferencia
    Given que envio uma requisicao DELETE Remove uma transferencia
    Then a API Transferencia deve retornar o status code 204

  @CT-1007 @TransferenciaContas
  Scenario: Validar transferencia entre contas com valor divergente
    Given que envio a requisicao de POST para realizar uma transferencia de R$ 1.00 entre contas
    Then a API Transferencia deve retornar o status code 422

  @CT-1008 @transferenciaValorAcima
  Scenario: Transferir valor acima de R$5000,00 com autenticação
    Given que a conta de origem possui saldo de R$6000,00
    And a conta de destino está ativa
    And o token de autenticação "123456" é fornecido
    When uma transferência de R$6000,00 é realizada
    Then a transferência é processada com sucesso

