@All @FuncionalideProdutos
Feature: Consulte produtos cadastrados
    Como um usuário do sistema
    Eu quero consultar produtos cadastrados
    Para que eu possa verificar a disponibilidade e detalhes dos produtos

  Background: Cadastro e login
    Given que cadastro um usuario valido para os testes
    And envio a requisição de login com usuario cadastrado

  @CT-2001
  Scenario: Validar cadastro de produto válido para os testes
    Given que cadastro um produto válido para os testes
    Then deve retornar produto com status code 201 e mensagem 'Cadastro realizado com sucesso'

    @CT-2002
    Scenario: Validar busca de produto por ID
      Given que envio a solicitação de GET com id do produto
      Then deve retornar produto com status code 200

    @CT-2003
    Scenario: Validar listar produtos cadastrados
      Given envio a solicitação de GET para listar todos os produtos
      Then deve retornar produto com status code 200

    @CT-2004
    Scenario: Validar edição de produto
      Given que envio a requisição PUT com id do produto para editar
      Then deve retornar produto com status code 200

    @CT-2005
    Scenario: Validar exclusão de produto
      Given envio uma solicitação de DELETE para excluir o produto
      Then deve retornar produto com status code 200