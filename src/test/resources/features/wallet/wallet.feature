@Wallet
Feature: Wallet - VisaCheckout
  Como um usuário do sistema
  Eu quero realizar pagamentos usando VisaCheckout
  Para ter uma experiência de pagamento mais segura e rápida

  @visaCheckoutDescriptografia
  Scenario: Realizar pagamento com VisaCheckout usando cartão descriptografado
    Given que estou na pagina de pagamento da carteira
    When realizo o pagamento com VisaCheckout
      | merchantOrderId | 2014111703          |
      | amount          | 15700               |
      | installments    | 1                   |
      | securityCode    | 123                 |
      | walletKey       | 1140814777695873901 |
    Then valido que o pagamento foi autorizado com sucesso
