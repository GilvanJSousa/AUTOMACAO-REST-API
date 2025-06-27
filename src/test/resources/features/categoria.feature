@categoria
Feature: Validar Operações relacionadas a categoria
  Como
  Eu
  Quero

  Background: Criar um novo usuário CMS com sucesso
    Given que envio uma requisição de registro de usuario CMS
    When o sistema processa a requisição
    Then eu envio a requisição de login com as credenciais do usuário

  @CT-12001
  Scenario: Validar Criar um novo categoria
    Given que envio uma requisição de cadastro de categoria
    When o sistema processa a requisição
    Then a API Categoria deve retornar o código de status 201

  @CT-12002
  Scenario: Listar Categorias
    When eu envio a requisição de listar Categorias com autenticação
    Then a API Categoria deve retornar o código de status 200
#    And os dados do usuário devem ser retornados na resposta

  @CT-12003
  Scenario: Buscar categoria por ID
    Given eu envio a requisição de busca de categorias por ID
    Then a API Categoria deve retornar o código de status 200

  @CT-12004
  Scenario: Atualizar categoria
    Given eu envio a requisição de PUT com ID
    Then a API Categoria deve retornar o código de status 200

  @CT-12005
  Scenario: Validar exclusão de categoria
    Given eu envio a requisição de DELETE para o ID
    Then deve retornar o status code 204 para exclusão