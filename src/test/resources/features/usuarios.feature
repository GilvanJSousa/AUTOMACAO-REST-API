@Usuario
Feature: Gerenciamento de Usuários CMS
  Como um administrador de sistema
  Eu quero gerenciar usuários CMS
  Para que eu possa controlar o acesso ao sistema

  @CT-1001
  Scenario: Criar um novo usuário CMS com sucesso
    Given que envio uma requisição de registro de usuario CMS
    Then a API deve retornar o código de status 201

  @CT-1002
  Scenario: Realizar login com usuário CMS
    Given eu envio a requisição de login com as credenciais do usuário
    Then a API deve retornar o código de status 200

  @CT-1003
  Scenario: Buscar a lista de usuários CMS com autenticação
    When eu envio a requisição de listar de usuários com autenticação
    Then a API deve retornar o código de status 200

  @CT-1004
  Scenario: Busca de usuario por ID
    When eu envio a requisição de busca de usuário por ID
    Then a API deve retornar o código de status 200

  @CT-1005
  Scenario: Validar alteração de usuário
    When que envio a solicitação de PUT com ID
    Then valido o retorno usuario atualizado com status code 200 e mensagem 'Usuário atualizado ou sem alterações'

  @CT-1006
  Scenario: Validar exclusão de usuario
    When envio uma solicitação de DELETE para o ID
    Then deve retornar o status code 204 para exclusão