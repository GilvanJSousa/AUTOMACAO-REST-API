@all @AVS
Feature: Teste de AVS (Address Verification System)
  Como um usuário do sistema
  Eu quero realizar um pagamento com verificação de endereço
  Para garantir a segurança da transação

  Scenario: Realizar pagamento com verificação de endereço
    Given que estou na pagina de pagamento com AVS
    When realizo o pagamento com cartao de credito e AVS
    Then valido que o pagamento foi autorizado com sucesso com AVS
    And validar o status code 201 da 'API AVS'
