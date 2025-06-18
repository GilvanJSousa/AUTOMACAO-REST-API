@FuncionalidadesCarrinhos
Feature: Consulte os carrinhos cadastrados
    Como um usuário do sistema
    Eu quero consultar os carrinhos cadastrados
    Para que eu possa verificar a disponibilidade e detalhes dos carrinhos

  Background: Cadastro e login
    Given que cadastro um usuario valido para os testes
    And envio a requisição de login com usuario cadastrado

  @CT-3001
  Scenario: Validar cadastro de carrinho válido para os testes
    Given que cadastro um carrinho válido para os testes
    Then deve retornar carrinho com status code 201 e mensagem 'Cadastro realizado com sucesso'

  @CT-3002
  Scenario: Validar busca de carrinho por ID
    Given que envio a solicitação de GET com id do carrinho
    Then deve retornar carrinho com status code 200

  @CT-3003
  Scenario: Validar listar carrinhos cadastrados
    Given envio a solicitação de GET para listar todos os carrinhos
    Then deve retornar carrinho com status code 200

  @CT-3004
  Scenario: Validar edição de carrinho
    Given que envio a requisição PUT com id do carrinho para editar
    Then deve retornar carrinho com status code 200

  @CT-3005
  Scenario: Validar exclusão de carrinho
    Given envio uma solicitação de DELETE para excluir o carrinho
    Then deve retornar carrinho com status code 200