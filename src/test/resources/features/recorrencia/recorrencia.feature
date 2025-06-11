@all @Recorrencia
Feature: Pagamento Recorrente com Cartão de Crédito
  Como um usuário do sistema
  Eu quero configurar um pagamento recorrente com cartão de crédito
  Para que eu possa automatizar minhas compras

  @RecorrenciaBimestral @CT-2001
  Scenario: Configurar pagamento recorrente bimestral
    Given que eu tenho um cartão de crédito válido para recorrência
    When eu envio a requisição de pagamento recorrente
    Then o pagamento recorrente deve ser processado com sucesso

  @ConsultaRecorrencia @CT-2002
  Scenario: Consultar pagamento recorrente
    Given que eu tenho um ID de pagamento recorrente válido
    When eu envio a requisição de consulta do pagamento recorrente
    Then a consulta do pagamento recorrente deve ser processada com sucesso

  @AlterarDadosComprador @CT-2003
  Scenario: Alterar dados do comprador em um pagamento recorrente
    Given que eu tenho um ID de pagamento recorrente válido
    When eu envio a requisição para alterar os dados do comprador
    Then a alteração dos dados do comprador deve ser processada com sucesso

  @AlterarIntervalo @CT-2004
  Scenario: Alterar intervalo do pagamento recorrente
    Given que eu tenho um ID de pagamento recorrente válido
    When eu envio a requisição para alterar o intervalo do pagamento recorrente
    Then a alteração do intervalo deve ser processada com sucesso

