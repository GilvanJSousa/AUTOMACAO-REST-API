@Usuario
Feature: Gerenciamento de Usuários CMS
  Como um administrador de sistema
  Eu quero gerenciar usuários CMS
  Para que eu possa controlar o acesso ao sistema

  @CT-1001
  Scenario: Criar um novo usuário CMS com sucesso
    Given que envio uma requisição de registro de usuario CMS
    Then a API deve retornar o código de status 201