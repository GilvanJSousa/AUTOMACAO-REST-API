@artigo
Feature: Validar Operações relacionadas a artigos
  Como
  Eu
  Quero

  Background: Criar um novo usuário CMS com sucesso
    Given que envio uma requisição de registro de usuario CMS
    When o sistema processa a requisição
    Then eu envio a requisição de login com as credenciais do usuário

  @CT-13001
  Scenario: Validar Criar um novo artigo
    Given que envio uma requisição de cadastro de categoria
    When o sistema processa a requisição
    Then a API Categoria deve retornar o código de status 201

  @CT-13002
  Scenario: Listar artigos
    When eu envio a requisição de listar Artigos com autenticação
    Then a API Categoria deve retornar o código de status 200
#    And os dados do usuário devem ser retornados na resposta

  @CT-13003
  Scenario: Buscar artigos por ID
    Given eu envio a requisição de busca de artigos por ID
    Then a API Artigos deve retornar o código de status 200

  @CT-13004
  Scenario: Atualizar artigos
    Given eu envio a requisição de PUT com ID
    Then a API Artigos deve retornar o código de status 200

  @CT-13005
  Scenario: Validar exclusão de artigos
    Given eu envio a requisição de DELETE para o ID
    Then deve retornar o status code 204 para exclusão