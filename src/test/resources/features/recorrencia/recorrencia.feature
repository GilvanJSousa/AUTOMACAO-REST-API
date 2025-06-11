@Recorrencia
Feature: Pagamento Recorrente com Cartão de Crédito
  Como um usuário do sistema
  Eu quero configurar um pagamento recorrente com cartão de crédito
  Para que eu possa automatizar minhas compras

  @RecorrenciaBimestral @CT-2001
  Scenario: Configurar pagamento recorrente bimestral
    Given que eu tenho um cartão de crédito válido para recorrência
    When eu envio a requisição de pagamento recorrente
    Then o pagamento recorrente deve ser processado com sucesso
    And valido o status code 201 para 'API recorrencia'

