@transferencia
Feature: Validar funcionalidade de Transferencias entre contas
  Como usuario do sistema
  Eu quero realizar transferencias
  Para

  @TransferenciaContas
  Scenario: Validar transferencia entre contas
    Given que envio a solicitação de POST para realizar uma transferencia entre contas
    Then a API Transferencia deve retornar o status code 201
    
  @TransferenciasLista
  Scenario: Validar a lista das transferencias realizadas
    Given que envio a solicitação de GET para Listar as transferências realizadas
    Then a API Transferencia deve retornar o status code 200

   @TransferenciasEspecifica
   Scenario: Validar consulta de transferencia especifica
     Given que envio a solicitação de GET para consultar transferencia especifica
     Then a API Transferencia deve retornar o status code 200

   @TransferenciaAtuailizacaoCompleta
   Scenario: Validar atualização completa de uma transação
     Given que envio uma requisição PUT atualiza completamente uma transferência
     Then a API Transferencia deve retornar o status code 204

