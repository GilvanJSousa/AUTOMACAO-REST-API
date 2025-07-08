@transferencia
Feature: Validar funcionalidade de Transferencias entre contas
  Como usuario do sistema
  Eu quero realizar transferencias
  Para

  @CT-1001 @TransferenciaContas
  Scenario: Validar transferencia entre contas
    Given que envio a requisicao de POST para realizar uma transferencia entre contas
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
     Given que envio uma requisicao PUT atualiza completamente uma transferencia
     Then a API Transferencia deve retornar o status code 204

  @CT-1005 @TransferenciaParcial
   Scenario: Validar atualizacao parcial de uma transferencia
     Given que envio uma requisicao PATCH atualiza parcialmente uma transferencia
     Then a API Transferencia deve retornar o status code 204

  @CT-1006 @TransferenciaParcial
  Scenario: Validar Remocao de uma transferencia
    Given que envio uma requisicao DELETE Remove uma transferencia
    Then a API Transferencia deve retornar o status code 204

