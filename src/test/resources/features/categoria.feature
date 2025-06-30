@categoria
Feature: Validar Operações relacionadas a categoria
  Como
  Eu
  Quero

  Background: Criar um novo usuário e efetuar login CMS com sucesso
    Given que envio uma requisição de registro de usuario CMS
    When o sistema processa a requisição
    Then eu envio a requisição de login com as credenciais do usuário

  @CT-2001
  Scenario: Validar Criar um novo categoria
    Given que envio uma requisição de cadastro de categoria
    When o sistema processa a requisição
    Then a API Categoria deve retornar o código de status 201

  @CT-2002
  Scenario: Listar Categorias
    When eu envio a requisição de listar Categorias com autenticação
    Then a API Categoria deve retornar o código de status 200

  @CT-2003
  Scenario: Buscar categoria por ID
    Given eu envio a requisição de busca de categorias por ID
    Then a API Categoria deve retornar o código de status 200

  @CT-2004
  Scenario: Atualizar categoria
    Given eu envio a requisição de PUT com ID
    Then a API Categoria deve retornar o código de status 200

  @CT-2005
  Scenario: Validar exclusão de categoria
#    Given que envio uma requisição de cadastro de categoria
#    When o sistema processa a requisição
#    Then a API Categoria deve retornar o código de status 201
    Given eu envio a requisição de DELETE para o ID
    Then a API categoria deve retornar o status code 204 para exclusão