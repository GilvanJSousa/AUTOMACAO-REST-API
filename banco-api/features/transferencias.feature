Feature: Serviço de Transferências

  Rule: Realizar Transferência
    Scenario: Transferir valor dentro do limite sem autenticação
      Given que a conta de origem possui saldo de R$500,00
      And a conta de destino está ativa
      When uma transferência de R$100,00 é realizada
      Then a transferência é processada com sucesso

    Scenario: Transferir valor acima de R$5000,00 com autenticação
      Given que a conta de origem possui saldo de R$6000,00
      And a conta de destino está ativa
      And o token de autenticação "123456" é fornecido
      When uma transferência de R$6000,00 é realizada
      Then a transferência é processada com sucesso

    Scenario: Transferir valor abaixo do limite mínimo
      Given que a conta de origem possui saldo de R$100,00
      And a conta de destino está ativa
      When uma transferência de R$5,00 é realizada
      Then o sistema retorna um erro indicando que o valor mínimo é de R$10,00

  Rule: Consultar Transferências
    Scenario: Consultar transferências de forma paginada
      Given que várias transferências foram realizadas
      When a consulta de transferências é realizada com limite de 10 itens por página e na página 1
      Then o sistema retorna uma lista com no máximo 10 transferências

  Rule: Atualizar Transferência
    Scenario: Atualizar todos os dados de uma transferência
      Given que a transferência de R$100,00 foi realizada entre a conta de origem "123" e a conta de destino "456"
      When a transferência é atualizada com novos dados (valor: R$200,00, conta de destino: "789")
      Then todos os dados da transferência são atualizados com sucesso

  Rule: Modificar Transferência
    Scenario: Modificar o valor de uma transferência
      Given que a transferência de R$50,00 foi realizada entre a conta de origem "123" e a conta de destino "456"
      When o valor da transferência é modificado para R$100,00
      Then a transferência é modificada com sucesso

  Rule: Remover Transferência
    Scenario: Remover uma transferência e reverter saldos
      Given que a transferência de R$200,00 foi realizada entre a conta de origem "123" e a conta de destino "456"
      When a transferência é removida
      Then o saldo da conta de origem e da conta de destino é revertido

    Scenario: Tentar remover uma transferência inexistente
      Given que não existe transferência com o ID "999"
      When a tentativa de remoção da transferência é realizada
      Then o sistema retorna um erro indicando que a transferência não foi encontrada
