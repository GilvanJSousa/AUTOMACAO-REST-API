@transferencia
Feature: Validar funcionalidade de Transferencias entre contas
  Como usuario do sistema
  Eu quero realizar transferencias
  Para

  @CT-1001 @TransferenciaContas
  Scenario: Validar transferencia entre contas
    Given que envio a requisicao de 'POST' para realizar uma transferencia no valor de R$ 11.00 entre contas
    Then a API Transferencia deve retornar o status code 201

  @CT-1002 @TransferenciasLista
  Scenario: Validar a lista das transferencias realizadas
    Given que envio a requisicao de 'GET' para Listar as transferencias realizadas
    Then a API Transferencia deve retornar o status code 200

  @CT-1003 @TransferenciasEspecifica
   Scenario: Validar consulta de transferencia especifica
     Given que envio a requisicao de 'GET' para consultar transferencia especifica
     Then a API Transferencia deve retornar o status code 200

  @CT-1004 @TransferenciaAtualizacaoCompleta
   Scenario: Validar atualizacao completa de uma transferencia
     Given que envio uma requisicao 'PUT' atualizar completamente o valor R$ 12.00 da transferencia
     Then a API Transferencia deve retornar o status code 204

  @CT-1005 @TransferenciaParcial
   Scenario: Validar atualizacao parcial de uma transferencia
     Given que envio uma requisicao 'PATCH' atualiza parcialmente em valor R$ 13.00 da transferencia
     Then a API Transferencia deve retornar o status code 204

  @CT-1006 @TransferenciaRemovida
  Scenario: Validar Remocao de uma transferencia
    Given que envio uma requisicao 'DELETE' Remove uma transferencia
    Then a API Transferencia deve retornar o status code 204

  @CT-1007 @TransferenciaContasDivergente
  Scenario: Validar transferencia entre contas com valor divergente
    Given que envio a requisicao de 'POST' para realizar uma transferencia de R$ 1.00 entre contas
    Then a API Transferencia deve retornar o status code 422

  @CT-1008 @transferenciaValorAcima
  Scenario: Transferir valor acima de R$ 100.00 com autenticação
    Given que a conta de origem possui saldo de R$ 1000.00
    And a conta de destino esta ativa
    And o token de autenticacao 'yJhbGciOi...' e fornecido
    When uma transferencia de R$ 100.00 e realizada
    Then a transferencia e processada com sucesso

  @CT-1009 @TransferenciaValorDentro
  Scenario: Transferir valor dentro do limite sem autenticação
    Given que a conta de origem possui saldo de R$ 100.00
    And a conta de destino esta ativa
    When uma transferencia de R$ 30.00 e realizada
    Then a transferencia e processada com sucesso

  @CT-1010 @TransferenciaValorBaixo
  Scenario: Transferir valor abaixo do limite mínimo
    Given que a conta de origem possui saldo de R$ 100.00
    And a conta de destino esta ativa
    When uma transferencia de R$ 5.00 e realizada com validacao de erro
    Then o sistema retorna um erro indicando que o valor minimo e de R$ 10.00

  @CT-1011 @TransferenciaAtualizarDados
  Scenario Outline: Atualizar todos os dados de uma transferência
    Given que a transferencia de R$ 80.00 foi realizada entre a conta de origem "<CONTA_ORIGEM>" e a conta de destino "<CONTA_DESTINO>"
    When a transferencia e atualizada com novos dados valor R$ 120.00 conta de destino "<CONTA_DESTINO_NOVO>"
    Then todos os dados da transferencia sao atualizados com sucesso

  Examples:
    | CONTA_ORIGEM             | CONTA_DESTINO            | CONTA_DESTINO_NOVO       |
    | 686fa208cbdb4375dbb8ed48 | 686fa208cbdb4375dbb8ed47 | 686fa208cbdb4375dbb8ed46 |

  @CT-1012 @TransferenciaModificarValor
  Scenario Outline: Modificar o valor de uma transferência
    Given que a transferencia de R$ 49.00 foi realizada entre a conta de origem "<CONTA_ORIGEM>" e a conta de destino "<CONTA_DESTINO>"
    When o valor da transferencia e modificado para R$ 51.00
    Then a transferencia e modificada com sucesso

    Examples:
      | CONTA_ORIGEM             | CONTA_DESTINO            |
      | 686fa208cbdb4375dbb8ed46 | 686fa208cbdb4375dbb8ed47 |

  @CT-1013 @TransferenciaRemover
  Scenario Outline: Remover uma transferência e reverter saldos
    Given que a transferencia de R$ 90.00 foi realizada entre a conta de origem "<CONTA_ORIGEM>" e a conta de destino "<CONTA_DESTINO>"
    When a transferencia e removida
    Then o saldo da conta de origem e da conta de destino e revertido

    Examples:
      | CONTA_ORIGEM             | CONTA_DESTINO            |
      | 686fa208cbdb4375dbb8ed47 | 686fa208cbdb4375dbb8ed48 |
      | CONTA_ORIGEM             | CONTA_DESTINO            |
      | 686fa208cbdb4375dbb8ed47 | 686fa208cbdb4375dbb8ed48 |

  @CT-1014 @TransferenciaRemoverInexistente
  Scenario Outline: Tentar remover uma transferencia inexistente
    Given que nao existe transferancia com o ID "<ID>"
    When a tentativa de remoção da transferencia e realizada
    Then o sistema retorna um erro indicando que a transferencia nao foi encontrada

    Examples:
      | ID                       |
      | 686fa208cbdb4375dbb8ed47 |

  @CT-1015 @TransferenciaContaInativa
  Scenario Outline: Validar a conta inativa e o saldo
    Given que a conta de origem "<CONTA_ORIGEM>" possui saldo de R$ 1000.00
    And a conta de destino "<CONTA_DESTINO>" esta inativa
    And o token de autenticacao 'yJhbGciOi...' e fornecido
    When uma transferencia de R$ 100.00 e realizada
    Then o sistema retorna um erro indicando que a Conta de origem ou destino esta inativa.

    Examples:
      | CONTA_ORIGEM             | CONTA_DESTINO            |
      | 686fa208cbdb4375dbb8ed47 | 686fa208cbdb4375dbb8ed49 |