@binQuery
Feature: Validação de BIN de cartão
  Como usuário do sistema
  Eu quero validar o BIN de cartões
  Para identificar a bandeira e tipo do cartão

  @binQuery @binQueryVisa
  Scenario: Validar BIN de cartão Visa
    Given que estou na pagina de consulta de BIN
    When realizo a consulta do BIN '401200'
    Then valido que o BIN foi consultado com sucesso
    And valido o status code do 'BinQuery' ser 200

  @binQuery @binQueryElo
  Scenario: Validar BIN de cartão Elo
    Given que estou na pagina de consulta de BIN
    When realizo a consulta do BIN '650518'
    Then valido que o BIN foi consultado com sucesso
    And valido o status code do 'BinQuery' ser 200

  @binQuery @binQueryMastercard
  Scenario: Validar BIN de cartão Mastercard
    Given que estou na pagina de consulta de BIN
    When realizo a consulta do BIN '422463'
    Then valido que o BIN foi consultado com sucesso
    And valido o status code do 'BinQuery' ser 200

  @binQuery @binQueryMastercard2
  Scenario: Validar BIN de cartão Mastercard 2
    Given que estou na pagina de consulta de BIN
    When realizo a consulta do BIN '550118'
    Then valido que o BIN foi consultado com sucesso
    And valido o status code do 'BinQuery' ser 200