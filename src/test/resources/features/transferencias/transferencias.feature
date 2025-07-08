@transferencia
Feature: Validar funcionalidade de Transferencias entre contas
  Como usuario do sistema
  Eu quero realizar transferencias
  Para

  @TransferenciaContas
  Scenario: Validar transferencia entre contas
    Given que envio a requisicao de POST para realizar uma transferencia entre contas
    Then a API Transferencia deve retornar o status code 201
    
  @TransferenciasLista
  Scenario: Validar a lista das transferencias realizadas
    Given que envio a requisicao de GET para Listar as transferencias realizadas
    Then a API Transferencia deve retornar o status code 200

   @TransferenciasEspecifica
   Scenario: Validar consulta de transferencia especifica
     Given que envio a requisicao de GET para consultar transferencia especifica
     Then a API Transferencia deve retornar o status code 200

   @TransferenciaAtualizacaoCompleta
   Scenario: Validar atualizacao completa de uma transferencia
     Given que envio uma requisicao PUT atualiza completamente uma transferencia
     Then a API Transferencia deve retornar o status code 204

   @TransferenciaParcial
   Scenario: Validar atualizacao parcial de uma transferencia
     Given que envio uma requisicao PATCH atualiza parcialmente uma transferencia
     Then a API Transferencia deve retornar o status code 204

  @TransferenciaParcial
  Scenario: Validar Remocao de uma transferencia
    Given que envio uma requisicao DELETE Remove uma transferencia
    Then a API Transferencia deve retornar o status code 204

