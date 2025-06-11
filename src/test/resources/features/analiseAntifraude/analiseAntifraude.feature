@Antifraude
Feature: Analise de Antifraude
  Como um usuário do sistema
  Eu quero realizar uma análise de antifraude em uma transação
  Para garantir a segurança da operação

  @CT-5001
  Scenario: Realizar analise de antifraude com sucesso
    Given que estou na pagina de pagamento
    When preparo a requisicao de antifraude
    And valido que o status code e 201 