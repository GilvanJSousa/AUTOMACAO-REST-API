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


